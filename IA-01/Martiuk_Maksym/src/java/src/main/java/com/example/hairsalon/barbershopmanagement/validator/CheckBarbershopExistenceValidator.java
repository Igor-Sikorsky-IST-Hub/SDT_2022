package com.example.hairsalon.barbershopmanagement.validator;

import com.example.hairsalon.repository.BarbershopRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class CheckBarbershopExistenceValidator implements ConstraintValidator<CheckBarbershopExistence, String> {

    private final BarbershopRepository barbershopRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return nonNull(barbershopRepository.findByName(value).orElse(null));
    }

}
