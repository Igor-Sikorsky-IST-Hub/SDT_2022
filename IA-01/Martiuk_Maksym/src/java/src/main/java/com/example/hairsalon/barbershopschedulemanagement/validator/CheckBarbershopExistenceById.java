package com.example.hairsalon.barbershopschedulemanagement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckBarbershopExistenceByIdValidator.class)
public @interface CheckBarbershopExistenceById {

    String message() default "Barbershop with such id doesn't exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
