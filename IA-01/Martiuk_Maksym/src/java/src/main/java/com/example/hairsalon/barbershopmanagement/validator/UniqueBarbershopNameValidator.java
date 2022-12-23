package com.example.hairsalon.barbershopmanagement.validator;

import com.example.hairsalon.barbershopmanagement.dto.BarbershopCreationDto;
import com.example.hairsalon.entity.Barbershop;
import com.example.hairsalon.repository.BarbershopRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class UniqueBarbershopNameValidator implements ConstraintValidator<UniqueBarbershopName, BarbershopCreationDto> {

    private final BarbershopRepository barbershopRepository;

    @Override
    public boolean isValid(BarbershopCreationDto dto, ConstraintValidatorContext context) {
        Barbershop barbershop = barbershopRepository.findByName(dto.getName())
                .orElse(null);

        if (nonNull(barbershop)) {

            if (nonNull(dto.getBarbershopId()) && dto.getBarbershopId() > 0) {
                return dto.getName().equals(barbershop.getName())
                        && dto.getBarbershopId().equals(barbershop.getId());
            }

            return !dto.getName().equals(barbershop.getName());

        }

        return true;
    }
}
