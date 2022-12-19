package com.example.hairsalon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Integer id;
    private DayOfWeek dayOfWeek;
    private LocalTime opening;
    private LocalTime closing;
    private boolean active;
    private Integer barbershopId;

}
