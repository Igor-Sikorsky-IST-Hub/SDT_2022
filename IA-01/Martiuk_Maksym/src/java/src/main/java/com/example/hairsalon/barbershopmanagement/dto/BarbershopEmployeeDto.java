package com.example.hairsalon.barbershopmanagement.dto;

import com.example.hairsalon.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BarbershopEmployeeDto {

    private Integer barbershopId;
    private Integer userId;
    private Role establishedRole;

}
