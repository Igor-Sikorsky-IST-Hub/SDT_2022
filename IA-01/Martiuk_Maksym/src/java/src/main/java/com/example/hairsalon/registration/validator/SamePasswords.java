package com.example.hairsalon.registration.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SamePasswordsValidator.class)
public @interface SamePasswords {

    String message() default "Passwords should be the same!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
