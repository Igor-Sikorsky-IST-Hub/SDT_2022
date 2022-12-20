package com.example.hairsalon.barbershopschedulemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {

    private Integer scheduleId;
    private DayOfWeek dayOfWeek;
    private LocalTime opening;
    private LocalTime closing;
    private Integer barbershopId;

}
