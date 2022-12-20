package com.example.hairsalon.barbershopmanagement.service;

import com.example.hairsalon.barbershopmanagement.converter.BarbershopCreationDtoConverter;
import com.example.hairsalon.barbershopmanagement.dto.BarbershopCreationDto;
import com.example.hairsalon.entity.Barbershop;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.BarbershopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BarbershopCreationServiceImpl implements BarbershopCreationService {

    private final BarbershopRepository barbershopRepository;
    private final BarbershopPermissionCheckService permissionCheckService;

    @Override
    public Barbershop createBarbershop(BarbershopCreationDto dto) {
        var barbershop = BarbershopCreationDtoConverter.fromDto(dto);

        return barbershopRepository.save(barbershop);
    }

    @Override
    public Barbershop updateBarbershop(BarbershopCreationDto dto) {
        checkBarbershopExistenceById(dto.getBarbershopId());

        permissionCheckService.checkForPlatformAdminRoleOrBarbershopAdmin(dto.getBarbershopId());

        var barbershop = BarbershopCreationDtoConverter.fromDto(dto);

        return barbershopRepository.update(barbershop);
    }

    @Override
    public void deleteBarbershop(Integer barbershopId) {
        checkBarbershopExistenceById(barbershopId);

        barbershopRepository.deleteById(barbershopId);
    }

    private void checkBarbershopExistenceById(Integer barbershopId) {
        if (!barbershopRepository.existsById(barbershopId)) {
            throw new ResourceNotFoundException(getNotFoundBarbershopMessage(barbershopId));
        }
    }

    private String getNotFoundBarbershopMessage(Integer barbershopId) {
        return String.format("Cannot find barbershop with id: %s ", barbershopId);
    }

}
