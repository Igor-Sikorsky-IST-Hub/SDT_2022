package com.example.hairsalon.barbershopmanagement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueBarbershopNameValidator.class)
public @interface UniqueBarbershopName {

    String message() default "Barbershop with such name already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
