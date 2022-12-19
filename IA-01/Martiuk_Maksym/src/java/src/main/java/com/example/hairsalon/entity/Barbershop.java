package com.example.hairsalon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Barbershop {

    private Integer id;
    private String name;
    private String city;
    private Integer amountOfWorkplaces;

}
