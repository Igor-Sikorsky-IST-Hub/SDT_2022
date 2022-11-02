package com.example.hairsalon.orderreservation.validator;

import com.example.hairsalon.orderreservation.dto.OrderPrimaryDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartDateEqualToFinishValidator implements ConstraintValidator<StartDateEqualToFinish, OrderPrimaryDto> {

    @Override
    public boolean isValid(OrderPrimaryDto dto, ConstraintValidatorContext context) {
        return dto.getStart().toLocalDate().isEqual(dto.getFinish().toLocalDate());
    }

}
