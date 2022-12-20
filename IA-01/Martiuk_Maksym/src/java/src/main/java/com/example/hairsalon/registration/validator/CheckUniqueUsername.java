package com.example.hairsalon.registration.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUniqueUsernameValidator.class)
public @interface CheckUniqueUsername {

    String message() default "User with such username already exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
