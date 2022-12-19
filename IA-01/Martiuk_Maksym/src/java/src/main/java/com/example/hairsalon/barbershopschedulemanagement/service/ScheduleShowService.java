package com.example.hairsalon.barbershopschedulemanagement.service;

import com.example.hairsalon.entity.Schedule;

import java.util.List;

public interface ScheduleShowService {

    List<Schedule> showActiveBarbershopSchedules(Integer barbershopId);

}
