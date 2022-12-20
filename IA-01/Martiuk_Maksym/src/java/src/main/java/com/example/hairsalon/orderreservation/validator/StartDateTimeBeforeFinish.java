package com.example.hairsalon.orderreservation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateTimeBeforeFinishValidator.class)
public @interface StartDateTimeBeforeFinish {

    String message() default "Start date time must be before finish date!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
