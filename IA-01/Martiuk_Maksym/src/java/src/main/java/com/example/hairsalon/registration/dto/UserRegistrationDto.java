package com.example.hairsalon.registration.dto;

import com.example.hairsalon.profilemanagement.validator.UniqueEmail;
import com.example.hairsalon.registration.validator.CheckUniqueUsername;
import com.example.hairsalon.registration.validator.SamePasswords;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@SamePasswords
public class UserRegistrationDto {

    @CheckUniqueUsername
    private String username;

    private String password;

    private String repeatPassword;

    private Integer profileId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @Email(message = "Enter correct email")
    @UniqueEmail
    @NotBlank
    private String email;

}
