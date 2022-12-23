package com.example.hairsalon.barbershopmanagement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckBarbershopExistenceValidator.class)
public @interface CheckBarbershopExistence {

    String message() default "Barbershop with such name doesn't exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
