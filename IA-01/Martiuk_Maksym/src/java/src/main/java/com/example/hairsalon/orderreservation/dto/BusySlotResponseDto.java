package com.example.hairsalon.orderreservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BusySlotResponseDto {

    private DayOfWeek dayOfWeek;
    private LocalTime start;
    private LocalTime finish;
    private LocalDate localDate;
    private Integer barberProfileId;

}
