package com.example.hairsalon.registration.validator;

import com.example.hairsalon.registration.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SamePasswordsValidator implements ConstraintValidator<SamePasswords, UserRegistrationDto> {

    @Override
    public boolean isValid(UserRegistrationDto dto, ConstraintValidatorContext context) {
        return dto.getPassword().equals(dto.getRepeatPassword());
    }

}
