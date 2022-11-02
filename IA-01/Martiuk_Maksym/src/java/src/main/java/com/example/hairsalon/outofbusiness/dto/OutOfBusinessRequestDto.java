package com.example.hairsalon.outofbusiness.dto;

import com.example.hairsalon.barbershopmanagement.validator.CheckBarbershopExistence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OutOfBusinessRequestDto {

    private Integer id;

    @NotBlank
    private String cause;

    private LocalDateTime start;

    private LocalDateTime finish;

    @CheckBarbershopExistence
    private String barbershopName;

}
