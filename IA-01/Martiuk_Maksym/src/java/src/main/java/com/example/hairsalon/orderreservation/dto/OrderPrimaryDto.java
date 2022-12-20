package com.example.hairsalon.orderreservation.dto;

import com.example.hairsalon.orderreservation.validator.StartDateEqualToFinish;
import com.example.hairsalon.orderreservation.validator.StartDateTimeBeforeFinish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@StartDateTimeBeforeFinish
@StartDateEqualToFinish
public class OrderPrimaryDto {

    private LocalDateTime start;
    private LocalDateTime finish;

}
