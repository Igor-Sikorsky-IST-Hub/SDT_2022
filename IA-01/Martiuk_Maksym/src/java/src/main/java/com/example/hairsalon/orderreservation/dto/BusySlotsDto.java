package com.example.hairsalon.orderreservation.dto;

import com.example.hairsalon.barbershopmanagement.validator.CheckBarbershopExistence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BusySlotsDto {

    @CheckBarbershopExistence
    @NotBlank
    private String barbershopName;

    @Min(value = 0, message = "You cannot search for last busy slots")
    private int quantityOfAcceptedDays;

    private List<Integer> barberProfileIdList;

}
