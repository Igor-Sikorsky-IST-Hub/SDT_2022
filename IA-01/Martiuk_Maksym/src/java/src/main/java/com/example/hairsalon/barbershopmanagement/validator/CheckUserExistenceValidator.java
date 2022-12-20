package com.example.hairsalon.barbershopmanagement.validator;

import com.example.hairsalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class CheckUserExistenceValidator implements ConstraintValidator<CheckUserExistence, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return nonNull(userRepository.findByUsername(value).orElse(null));
    }

}
