package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.BarbershopRepository;
import com.example.hairsalon.repository.OrderRepository;
import com.example.hairsalon.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusySlotsServiceImpl implements BusySlotsService {

    private final OrderRepository orderRepository;
    private final BarbershopRepository barbershopRepository;
    private final ProfileRepository profileRepository;

    @Override
    public List<Order> busySlots(String barbershopName, int quantityOfAcceptedDays, List<Integer> barberProfileIdList) {
        var barbershopId = getBarbershopId(barbershopName);

        var barbershopOrders = getOrdersByBarbershop(barbershopId);
        var upcomingOrders = getUpcomingOrders(barbershopOrders);

        if (barberProfileIdList.isEmpty()) {
            return upcomingOrders;
        }

        checkProfileIdExistence(barberProfileIdList);


        return upcomingOrders.stream()
                .filter(order -> barberProfileIdList.contains(order.getBarberProfileId()))
                .toList();
    }

    private void checkProfileIdExistence(List<Integer> barberProfileIdList) {
        var profiles = profileRepository.findAllByIdList(barberProfileIdList);

        var listOfDbProfileId = profiles.stream()
                .map(Profile::getId)
                .toList();

        var notExistedProfiles = barberProfileIdList.stream()
                .filter(profileId -> !listOfDbProfileId.contains(profileId))
                .toList();

        if (!notExistedProfiles.isEmpty()) {
            throw new ResourceNotFoundException(String.format(
                    "Cannot find profiles with id: %s",
                    notExistedProfiles.stream()
                            .map(value -> Integer.toString(value))
                            .collect(Collectors.joining(", "))));
        }
    }

    private Integer getBarbershopId(String barbershopName) {
        return barbershopRepository.findByName(barbershopName).orElseThrow(
                () -> new ResourceNotFoundException(getNotFoundBarbershopByItsNameMessage(barbershopName))
        ).getId();
    }

    private List<Order> getUpcomingOrders(List<Order> barbershopOrders) {
        return barbershopOrders.stream()
                .filter(order -> {
                    LocalDate now = LocalDate.now();

                    return order.getStart().toLocalDate().isAfter(now)
                            || order.getStart().toLocalDate().isEqual(now);
                })
                .toList();
    }

    private List<Order> getOrdersByBarbershop(Integer barbershopId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getBarbershopId().equals(barbershopId))
                .toList();
    }

    private String getNotFoundBarbershopByItsNameMessage(String barbershopName) {
        return String.format("Cannot find barbershop with name: %s", barbershopName);
    }

}
