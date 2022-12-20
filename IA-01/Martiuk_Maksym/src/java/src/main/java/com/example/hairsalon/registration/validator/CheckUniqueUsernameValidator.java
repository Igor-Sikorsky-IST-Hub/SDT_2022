package com.example.hairsalon.registration.validator;

import com.example.hairsalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class CheckUniqueUsernameValidator implements ConstraintValidator<CheckUniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return isNull(userRepository.findByUsername(username).orElse(null));
    }

}
