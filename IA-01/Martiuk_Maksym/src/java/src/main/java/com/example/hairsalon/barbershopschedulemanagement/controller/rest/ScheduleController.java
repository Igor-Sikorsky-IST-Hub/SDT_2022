package com.example.hairsalon.barbershopschedulemanagement.controller.rest;

import com.example.hairsalon.barbershopschedulemanagement.converter.ScheduleResponseDtoConverter;
import com.example.hairsalon.barbershopschedulemanagement.dto.ScheduleRequestDto;
import com.example.hairsalon.barbershopschedulemanagement.dto.ScheduleResponseDto;
import com.example.hairsalon.barbershopschedulemanagement.service.ScheduleManagementService;
import com.example.hairsalon.barbershopschedulemanagement.service.ScheduleShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleManagementService managementService;
    private final ScheduleShowService scheduleShowService;

    @GetMapping("{barbershopId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ScheduleResponseDto> getAllBarbershop(@PathVariable("barbershopId") Integer barbershopId) {
        var barbershopSchedules = scheduleShowService.showActiveBarbershopSchedules(barbershopId);

        return ScheduleResponseDtoConverter.toDtoList(barbershopSchedules);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ScheduleResponseDto createSchedule(@Valid @RequestBody ScheduleRequestDto dto) {
        var schedule = managementService.createSchedule(dto);

        return ScheduleResponseDtoConverter.toDto(schedule);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @PostMapping("/{scheduleId}/activate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String activateSchedule(@PathVariable("scheduleId") Integer scheduleId) {
        managementService.activateSchedule(scheduleId);

        return String.format("Schedule with id: %d - successfully activated!", scheduleId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @PostMapping("/{scheduleId}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deactivateSchedule(@PathVariable("scheduleId") Integer scheduleId) {
        managementService.deactivateSchedule(scheduleId);

        return String.format("Schedule with id: %d - successfully deactivated!", scheduleId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @DeleteMapping("/{scheduleId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteSchedule(@PathVariable("scheduleId") Integer scheduleId) {
        managementService.deleteSchedule(scheduleId);

        return String.format("Schedule with id %d - successfully deleted!", scheduleId);
    }

}
