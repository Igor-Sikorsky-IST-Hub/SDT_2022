package com.example.hairsalon.orderreservation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckProfileExistenceValidator.class)
public @interface CheckProfileExistence {

    String message() default "Profile with such id doesn't exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
