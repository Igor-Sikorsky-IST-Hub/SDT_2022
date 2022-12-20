package com.example.hairsalon.barbershopmanagement.validator;

import com.example.hairsalon.entity.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositionRoleTypeValidator implements ConstraintValidator<PositionRoleType, Role> {

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        return Role.getPositionRoles().contains(role);
    }

}
