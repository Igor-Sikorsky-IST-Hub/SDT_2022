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
public class Order {

    private Integer id;
    private LocalDateTime start;
    private LocalDateTime finish;
    private OrderStatus status;
    private Integer clientProfileId;
    private Integer barberProfileId;
    private Integer barbershopId;

}
