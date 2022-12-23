package com.example.hairsalon.barbershopmanagement.dto;

import com.example.hairsalon.barbershopmanagement.validator.UniqueBarbershopName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@UniqueBarbershopName
public class BarbershopCreationDto {

    private Integer barbershopId;

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @Min(value = 1, message = "Amount of workplaces cannot be less then 1")
    private Integer amountOfWorkplaces;

}
