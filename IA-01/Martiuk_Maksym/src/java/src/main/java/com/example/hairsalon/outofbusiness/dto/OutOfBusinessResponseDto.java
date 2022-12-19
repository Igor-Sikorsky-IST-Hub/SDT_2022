package com.example.hairsalon.outofbusiness.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OutOfBusinessResponseDto {

    private Integer id;
    private String cause;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String barbershopName;

}
