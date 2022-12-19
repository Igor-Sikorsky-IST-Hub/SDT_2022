package com.example.hairsalon.orderreservation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateEqualToFinishValidator.class)
public @interface StartDateEqualToFinish {

    String message() default "Start of reservation date must be the same to finish date!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
