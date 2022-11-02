package com.example.hairsalon.barbershopmanagement.dto;

import com.example.hairsalon.barbershopmanagement.validator.CheckBarbershopExistence;
import com.example.hairsalon.barbershopmanagement.validator.CheckUserExistence;
import com.example.hairsalon.barbershopmanagement.validator.PositionRoleType;
import com.example.hairsalon.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SetupBarbershopEmployeeDto {

    //todo username -> userId, barbershopName -> barbershopId

    @CheckBarbershopExistence
    @NotBlank
    private String barbershopName;
    @CheckUserExistence
    @NotBlank
    private String username;
    @PositionRoleType
    private Role position;

}
