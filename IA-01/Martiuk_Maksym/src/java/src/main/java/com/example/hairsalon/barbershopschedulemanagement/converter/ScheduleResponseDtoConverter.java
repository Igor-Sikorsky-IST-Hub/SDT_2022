package com.example.hairsalon.barbershopschedulemanagement.converter;

import com.example.hairsalon.barbershopschedulemanagement.dto.ScheduleResponseDto;
import com.example.hairsalon.entity.Schedule;

import java.util.List;

public final class ScheduleResponseDtoConverter {

    private ScheduleResponseDtoConverter() {
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        var dto = new ScheduleResponseDto();
        dto.setScheduleId(schedule.getId());
        dto.setBarbershopId(schedule.getBarbershopId());
        dto.setOpening(schedule.getOpening());
        dto.setClosing(schedule.getClosing());
        dto.setDayOfWeek(schedule.getDayOfWeek());

        return dto;
    }

    public static List<ScheduleResponseDto> toDtoList(List<Schedule> schedules) {
        return schedules.stream()
                .map(ScheduleResponseDtoConverter::toDto)
                .toList();
    }

}
