package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Schedule;
import com.example.hairsalon.repository.rowmapper.ScheduleRowMapper;
import com.example.hairsalon.repository.util.JdbcUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.sql.Types.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ScheduleRowMapper scheduleRowMapper;

    @Override
    public Optional<Schedule> findById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("schedule_id", id);

        String query = "SELECT bs.id, bs.day_of_week, bs.opening, bs.closing, bs.active, bs.barbershop_id "
                + "FROM barbershop_schedules bs "
                + "WHERE bs.id = :schedule_id";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, parameterSource, scheduleRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for schedule id: {}", exception.getClass().getName(), id);
            return Optional.empty();
        }
    }

    @Override
    public List<Schedule> findAll() {
        String query = "SELECT bs.id, bs.day_of_week, bs.opening, bs.closing, bs.active, bs.barbershop_id "
                + "FROM barbershop_schedules bs";

        return jdbcTemplate.query(query, scheduleRowMapper);
    }

    @Override
    public Schedule save(Schedule schedule) {
        MapSqlParameterSource parameterSource = setupParamSourceForScheduleCredentials(schedule);
        GeneratedKeyHolder keyHolder = insertScheduleCredentials(parameterSource);
        Integer generatedId = JdbcUtil.getIdFromKeyHolder(keyHolder);
        schedule.setId(generatedId);

        return schedule;
    }

    @Override
    public Schedule update(Schedule schedule) {
        updateScheduleCredentials(schedule, setupParamSourceForScheduleCredentials(schedule));

        return schedule;
    }

    @Override
    public boolean existsById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("schedule_id", id, INTEGER);

        String query = "SELECT EXISTS(SELECT * FROM barbershop_schedules WHERE id = :schedule_id)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    @Override
    public void deleteById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("schedule_id", id, INTEGER);

        String query = "DELETE FROM barbershop_schedules WHERE id = :schedule_id";

        jdbcTemplate.update(query, parameterSource);
    }

    private MapSqlParameterSource setupParamSourceForScheduleCredentials(Schedule schedule) {
        return new MapSqlParameterSource()
                .addValue("barbershop_id", schedule.getBarbershopId(), INTEGER)
                .addValue("day_of_week", schedule.getDayOfWeek().name(), VARCHAR)
                .addValue("opening", schedule.getOpening(), TIME)
                .addValue("closing", schedule.getClosing(), TIME)
                .addValue("active", schedule.isActive(), BOOLEAN);
    }

    private GeneratedKeyHolder insertScheduleCredentials(MapSqlParameterSource parameterSource) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO barbershop_schedules(barbershop_id, day_of_week, opening, closing, active) "
                + "VALUES (:barbershop_id, :day_of_week, :opening, :closing, :active)";

        jdbcTemplate.update(query, parameterSource, keyHolder, new String[]{"id"});

        return keyHolder;
    }

    private void updateScheduleCredentials(Schedule schedule, MapSqlParameterSource parameterSource) {
        parameterSource.addValue("schedule_id", schedule.getId(), INTEGER);

        String query = "UPDATE barbershop_schedules "
                + "SET barbershop_id = :barbershop_id, day_of_week = :day_of_week, opening = :opening, "
                + "closing = :closing, active = :active "
                + "WHERE id = :schedule_id";

        jdbcTemplate.update(query, parameterSource);
    }

}
