package com.example.hairsalon.repository.rowmapper;

import com.example.hairsalon.entity.Schedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
public class ScheduleRowMapper implements RowMapper<Schedule> {

    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer scheduleId = rs.getObject("id", Integer.class);
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(rs.getString("day_of_week"));
        LocalTime opening = rs.getTime("opening").toLocalTime();
        LocalTime closing = rs.getTime("closing").toLocalTime();
        boolean active = rs.getBoolean("active");
        Integer barbershopId = rs.getObject("barbershop_id", Integer.class);

        return Schedule.builder()
                .id(scheduleId)
                .dayOfWeek(dayOfWeek)
                .opening(opening)
                .closing(closing)
                .active(active)
                .barbershopId(barbershopId)
                .build();
    }

}
