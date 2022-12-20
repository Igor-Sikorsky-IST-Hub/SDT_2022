package com.example.hairsalon.barbershopschedulemanagement.dto;

import com.example.hairsalon.barbershopschedulemanagement.validator.CheckBarbershopExistenceById;
import com.example.hairsalon.barbershopschedulemanagement.validator.OpeningBeforeClosing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@OpeningBeforeClosing
public class ScheduleRequestDto {

    private Integer scheduleId;
    private DayOfWeek dayOfWeek;
    private LocalTime opening;
    private LocalTime closing;
    @CheckBarbershopExistenceById
    private Integer barbershopId;

}
