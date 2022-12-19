package com.example.hairsalon.orderreservation.dto;

import com.example.hairsalon.barbershopmanagement.validator.CheckBarbershopExistence;
import com.example.hairsalon.orderreservation.validator.CheckProfileExistence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class OrderReservationDto extends OrderPrimaryDto {

    private Integer orderId;

    @NotBlank
    @CheckBarbershopExistence
    private String barbershopName;

    @CheckProfileExistence
    private Integer barberProfileId;

    @CheckProfileExistence
    private Integer clientProfileId;

}
