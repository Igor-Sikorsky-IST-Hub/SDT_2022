package com.example.hairsalon.barbershopschedulemanagement.validator;

import com.example.hairsalon.repository.BarbershopRepository;
import lombok.RequiredArgsConstructor;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class CheckBarbershopExistenceByIdValidator implements ConstraintValidator<CheckBarbershopExistenceById, Integer> {

    private final BarbershopRepository barbershopRepository;

    @Override
    public boolean isValid(Integer barbershopId, ConstraintValidatorContext context) {
        return barbershopRepository.existsById(barbershopId);
    }

}
