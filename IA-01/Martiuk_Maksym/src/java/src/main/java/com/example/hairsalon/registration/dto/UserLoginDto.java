package com.example.hairsalon.registration.dto;

import com.example.hairsalon.barbershopmanagement.validator.CheckUserExistence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    @CheckUserExistence
    private String username;
    private String password;

}
