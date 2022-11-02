package com.example.hairsalon.profilemanagement.validator;

import com.example.hairsalon.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final ProfileRepository profileRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return isNull(profileRepository.findByEmail(email).orElse(null));
    }

}
