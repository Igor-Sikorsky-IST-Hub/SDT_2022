package com.example.hairsalon.outofbusiness.service;

import com.example.hairsalon.entity.OutOfBusiness;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.BarbershopRepository;
import com.example.hairsalon.repository.OutOfBusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutOfBusinessCheckServiceImpl implements OutOfBusinessCheckService {

    private final OutOfBusinessRepository outOfBusinessRepository;
    private final BarbershopRepository barbershopRepository;

    @Override
    public List<OutOfBusiness> getAllFromOutOfBusinessCategory() {
        return outOfBusinessRepository.findAll();
    }

    @Override
    public OutOfBusiness getOutOfBusinessInfoIfInThisCategory(String barbershopName) {
        var outOfBusinesses = outOfBusinessRepository.findByBarbershopName(barbershopName);

        boolean isBarbershopExist = nonNull(barbershopRepository.findByName(barbershopName).orElse(null));

        return outOfBusinesses.stream()
                .filter(this::isOutOfBusinessNow)
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException(getOutOfBusinessNotFoundMessage(barbershopName, isBarbershopExist))
                );
    }

    private boolean isOutOfBusinessNow(OutOfBusiness outOfBusiness) {
        return isNull(outOfBusiness.getFinish()) || outOfBusiness.getFinish().isAfter(LocalDateTime.now());

    }

    private String getOutOfBusinessNotFoundMessage(String barbershopName, boolean isBarbershopExist) {
        return isBarbershopExist ?
                String.format("Barbershop %s in business now", barbershopName) :
                String.format("Cannot find barbershop with name: %s", barbershopName);
    }

}
