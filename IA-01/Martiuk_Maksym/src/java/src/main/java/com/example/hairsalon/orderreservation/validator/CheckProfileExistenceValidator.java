package com.example.hairsalon.orderreservation.validator;

import com.example.hairsalon.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class CheckProfileExistenceValidator implements ConstraintValidator<CheckProfileExistence, Integer> {

    private final ProfileRepository profileRepository;

    @Override
    public boolean isValid(Integer profileId, ConstraintValidatorContext context) {
        return profileRepository.existsById(profileId);
    }

}
