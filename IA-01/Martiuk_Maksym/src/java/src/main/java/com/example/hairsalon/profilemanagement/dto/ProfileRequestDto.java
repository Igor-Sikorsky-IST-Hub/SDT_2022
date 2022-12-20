package com.example.hairsalon.profilemanagement.dto;

import com.example.hairsalon.profilemanagement.validator.UniqueEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class ProfileRequestDto {

    private Integer profileId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @Email(message = "Enter correct email")
    @UniqueEmail
    @NotBlank
    private String email;

    private Integer userId;

}
