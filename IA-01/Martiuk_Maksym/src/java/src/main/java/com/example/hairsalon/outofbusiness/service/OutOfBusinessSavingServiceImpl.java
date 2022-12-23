package com.example.hairsalon.outofbusiness.service;

import com.example.hairsalon.email.dto.OutOfBusinessMailNotificationDto;
import com.example.hairsalon.email.service.OutOfBusinessMailService;
import com.example.hairsalon.entity.*;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.exception.ResourceWriteException;
import com.example.hairsalon.outofbusiness.dto.OutOfBusinessRequestDto;
import com.example.hairsalon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@Transactional
@RequiredArgsConstructor
public class OutOfBusinessSavingServiceImpl implements OutOfBusinessSavingService {

    private final OutOfBusinessRepository outOfBusinessRepository;
    private final OutOfBusinessMailService mailService;
    private final ClientRepository clientRepository;
    private final ProfileRepository profileRepository;
    private final BarbershopRepository barbershopRepository;
    private final OrderRepository orderRepository;

    @Override
    public OutOfBusiness saveToOutOfBusinessCategory(OutOfBusinessRequestDto dto) {
        var barbershop = getBarbershop(dto);
        checkIfBarbershopHasAlreadyBeenOutOfBusiness(barbershop.getId(), dto);

        var outOfBusiness = getOutOfBusinessEntityFromDto(dto, barbershop);
        var emails = getBarbershopClientsEmails(barbershop);
        var mailDto = getOutOfBusinessMailNotificationDto(dto, emails);
        var savedOutOfBusiness = outOfBusinessRepository.save(outOfBusiness);

        deleteAllBarbershopOrdersAfterOutOfBusinessStart(barbershop, outOfBusiness);

        mailService.sendMailNotificationAboutOutOfBusiness(mailDto);

        return savedOutOfBusiness;
    }

    @Override
    public OutOfBusiness updateOutOfBusinessCategory(OutOfBusinessRequestDto dto) {
        if (dto.getId() < 1 || isNull(dto.getId())) {
            throw new ResourceNotFoundException(String.format("Cannot found out of business salon with id: %d", dto.getId()));
        }

        var barbershop = getBarbershop(dto);

        var outOfBusiness = getOutOfBusinessEntityFromDto(dto, barbershop);
        var emails = getBarbershopClientsEmails(barbershop);
        var mailDto = getOutOfBusinessMailNotificationDto(dto, emails);

        mailService.sendMailNotificationAboutUpdatedOutOfBusiness(mailDto);

        return outOfBusinessRepository.update(outOfBusiness);
    }

    private OutOfBusinessMailNotificationDto getOutOfBusinessMailNotificationDto(
            OutOfBusinessRequestDto dto,
            List<String> emails
    ) {
        var mailDto = new OutOfBusinessMailNotificationDto();
        mailDto.setBarbershopName(dto.getBarbershopName());
        mailDto.setCause(dto.getCause());
        mailDto.setStart(dto.getStart());
        mailDto.setFinish(dto.getFinish());
        mailDto.setEmails(emails);

        return mailDto;
    }

    private List<String> getBarbershopClientsEmails(Barbershop barbershop) {
        var clients = clientRepository.findByBarbershopId(barbershop.getId());
        var clientIds = clients.stream()
                .map(Client::getClientProfileId)
                .toList();
        var profiles = profileRepository.findAllByIdList(clientIds);

        return profiles.stream()
                .map(Profile::getEmail)
                .toList();
    }

    private OutOfBusiness getOutOfBusinessEntityFromDto(
            OutOfBusinessRequestDto dto,
            Barbershop barbershop
    ) {
        return OutOfBusiness.builder()
                .id(dto.getId())
                .barbershopId(barbershop.getId())
                .cause(dto.getCause())
                .start(dto.getStart())
                .finish(dto.getFinish())
                .build();
    }

    private Barbershop getBarbershop(OutOfBusinessRequestDto dto) {
        return barbershopRepository.findByName(dto.getBarbershopName()).orElseThrow(
                () -> new ResourceNotFoundException(String.format(
                        "Cannot find barbershop with name %s", dto.getBarbershopName())
                )
        );
    }

    private void checkIfBarbershopHasAlreadyBeenOutOfBusiness(Integer barbershopId, OutOfBusinessRequestDto dto) {
        var count = (int) outOfBusinessRepository.findByBarbershopId(barbershopId).stream()
                .filter(outOfBusiness -> outOfBusiness.getFinish().isAfter(dto.getStart()))
                .count();

        if (count > 0) {
            throw new ResourceWriteException(
                    "Cannot add barbershop to this category since it has already been out of business"
            );
        }
    }

    private void deleteAllBarbershopOrdersAfterOutOfBusinessStart(Barbershop barbershop, OutOfBusiness outOfBusiness) {
        var ordersByBarbershop = orderRepository.findAll().stream()
                .filter(order -> order.getBarbershopId().equals(barbershop.getId()))
                .toList();

        var ordersForDeleting = ordersByBarbershop.stream()
                .filter(order -> orderDateTimeIsAfterOutOfBusiness(outOfBusiness, order))
                .toList();

        orderRepository.deleteAllInBatch(ordersForDeleting);
    }

    private boolean orderDateTimeIsAfterOutOfBusiness(OutOfBusiness outOfBusiness, Order order) {
        return order.getStart().isAfter(outOfBusiness.getStart());
    }

}
