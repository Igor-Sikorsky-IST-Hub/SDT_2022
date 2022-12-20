package com.example.hairsalon.barbershopschedulemanagement.service;

import com.example.hairsalon.barbershopschedulemanagement.dto.ScheduleRequestDto;
import com.example.hairsalon.entity.Schedule;

public interface ScheduleManagementService {

    Schedule createSchedule(ScheduleRequestDto dto);

    void deactivateSchedule(Integer scheduleId);

    void activateSchedule(Integer scheduleId);

    void deleteSchedule(Integer scheduleId);

}
