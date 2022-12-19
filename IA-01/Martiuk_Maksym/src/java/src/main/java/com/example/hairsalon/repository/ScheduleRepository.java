package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Optional<Schedule> findById(Integer id);

    List<Schedule> findAll();

    Schedule save(Schedule schedule);

    Schedule update(Schedule schedule);

    boolean existsById(Integer id);

    void deleteById(Integer id);

}
