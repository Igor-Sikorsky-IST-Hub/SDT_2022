package com.example.hairsalon.barbershopschedulemanagement.service;

import com.example.hairsalon.barbershopmanagement.service.BarbershopPermissionCheckService;
import com.example.hairsalon.barbershopschedulemanagement.dto.ScheduleRequestDto;
import com.example.hairsalon.entity.Schedule;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleManagementServiceImpl implements ScheduleManagementService {

    private final ScheduleRepository scheduleRepository;
    private final BarbershopPermissionCheckService permissionCheckService;

    @Override
    public Schedule createSchedule(ScheduleRequestDto dto) {
        permissionCheckService.checkForPlatformAdminRoleOrBarbershopAdmin(dto.getBarbershopId());

        var schedule = buildScheduleFromDto(dto);

        return scheduleRepository.save(schedule);
    }

    @Override
    public void deactivateSchedule(Integer scheduleId) {
        var schedule = getScheduleById(scheduleId);

        permissionCheckService.checkForPlatformAdminRoleOrBarbershopAdmin(schedule.getBarbershopId());

        if (schedule.isActive()) {
            schedule.setActive(false);
            scheduleRepository.update(schedule);
        }
    }

    @Override
    public void activateSchedule(Integer scheduleId) {
        var schedule = getScheduleById(scheduleId);

        permissionCheckService.checkForPlatformAdminRoleOrBarbershopAdmin(schedule.getBarbershopId());

        if (!schedule.isActive() && checkCorrectness(schedule)) {
            schedule.setActive(true);
            scheduleRepository.update(schedule);
        }
    }

    @Override
    public void deleteSchedule(Integer scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    private Schedule buildScheduleFromDto(ScheduleRequestDto dto) {
        return Schedule.builder()
                .dayOfWeek(dto.getDayOfWeek())
                .opening(dto.getOpening())
                .closing(dto.getClosing())
                .active(false)
                .barbershopId(dto.getBarbershopId())
                .build();
    }

    private Schedule getScheduleById(Integer scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(
                        "Cannot find schedule with id: %d", scheduleId
                ))
        );
    }

    private boolean checkCorrectness(Schedule schedule) {
        var barbershopId = schedule.getBarbershopId();
        var associatedSchedules = getBarbershopSchedules(barbershopId);
        var dayOfWeek = schedule.getDayOfWeek();

        int count = getCountOfActiveSchedules(associatedSchedules, dayOfWeek);

        return count == 0;
    }

    private int getCountOfActiveSchedules(List<Schedule> associatedSchedules, DayOfWeek dayOfWeek) {
        return (int) associatedSchedules.stream()
                .filter(iterSchedule -> iterSchedule.getDayOfWeek().equals(dayOfWeek))
                .filter(Schedule::isActive)
                .count();
    }

    private List<Schedule> getBarbershopSchedules(Integer barbershopId) {
        return scheduleRepository.findAll().stream()
                .filter(iterSchedule -> iterSchedule.getBarbershopId().equals(barbershopId))
                .toList();
    }

}
