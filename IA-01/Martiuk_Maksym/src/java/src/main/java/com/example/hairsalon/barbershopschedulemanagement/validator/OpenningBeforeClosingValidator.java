package com.example.hairsalon.barbershopschedulemanagement.validator;

import com.example.hairsalon.barbershopschedulemanagement.dto.ScheduleRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OpenningBeforeClosingValidator implements ConstraintValidator<OpeningBeforeClosing, ScheduleRequestDto> {

    @Override
    public boolean isValid(ScheduleRequestDto dto, ConstraintValidatorContext context) {
        return dto.getOpening().isBefore(dto.getClosing());
    }

}
