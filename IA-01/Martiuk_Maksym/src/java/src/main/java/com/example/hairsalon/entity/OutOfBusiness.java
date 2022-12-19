package com.example.hairsalon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutOfBusiness {

    private Integer id;
    private String cause;
    private LocalDateTime start;
    private LocalDateTime finish;
    private Integer barbershopId;

}
