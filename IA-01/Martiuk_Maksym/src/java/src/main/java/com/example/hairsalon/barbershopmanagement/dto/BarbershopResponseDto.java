package com.example.hairsalon.barbershopmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BarbershopResponseDto {

    private Integer id;
    private String name;
    private String city;
    private Integer amountOfWorkplaces;

}
