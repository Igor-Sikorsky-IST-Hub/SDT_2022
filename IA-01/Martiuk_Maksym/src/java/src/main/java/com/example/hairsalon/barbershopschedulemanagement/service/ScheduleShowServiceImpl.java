package com.example.hairsalon.barbershopschedulemanagement.service;

import com.example.hairsalon.entity.Schedule;
import com.example.hairsalon.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleShowServiceImpl implements ScheduleShowService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> showActiveBarbershopSchedules(Integer barbershopId) {
        return getActiveSchedules(barbershopId);
    }

    private List<Schedule> getActiveSchedules(Integer barbershopId) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getBarbershopId().equals(barbershopId))
                .filter(Schedule::isActive)
                .toList();
    }

}
