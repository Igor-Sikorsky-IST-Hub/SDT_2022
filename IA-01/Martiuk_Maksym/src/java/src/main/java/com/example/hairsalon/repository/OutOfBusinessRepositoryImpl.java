package com.example.hairsalon.repository;

import com.example.hairsalon.entity.OutOfBusiness;
import com.example.hairsalon.repository.rowmapper.OutOfBusinessRowMapper;
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
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OutOfBusinessRepositoryImpl implements OutOfBusinessRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OutOfBusinessRowMapper outOfBusinessRowMapper;

    @Override
    public Optional<OutOfBusiness> findById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("out_of_business_id", id, INTEGER);

        String query = "SELECT oob.id AS id, oob.cause AS cause, "
                + "oob.start AS start, oob.finish AS finish, oob.barbershop_id AS barbershop_id "
                + "FROM out_of_business oob "
                + "WHERE oob.id = :out_of_business_id";

        try {
            return ofNullable(jdbcTemplate.queryForObject(query, parameterSource, outOfBusinessRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for OutOfBusiness with id: {}", exception.getClass().getName(), id);
            return Optional.empty();
        }
    }

    @Override
    public List<OutOfBusiness> findByBarbershopId(Integer barbershopId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_id", barbershopId, INTEGER);

        String query = "SELECT oob.id AS id, oob.cause AS cause, "
                + "oob.start AS start, oob.finish AS finish, oob.barbershop_id AS barbershop_id "
                + "FROM out_of_business oob "
                + "WHERE oob.barbershop_id = :barbershop_id";

        return jdbcTemplate.query(query, parameterSource, outOfBusinessRowMapper);
    }

    @Override
    public List<OutOfBusiness> findAll() {
        String query = "SELECT oob.id AS id, oob.cause AS cause, "
                + "     oob.start AS start, oob.finish AS finish, oob.barbershop_id AS barbershop_id "
                + "FROM out_of_business oob";

        return jdbcTemplate.query(query, outOfBusinessRowMapper);
    }

    @Override
    public OutOfBusiness save(OutOfBusiness outOfBusiness) {
        MapSqlParameterSource parameterSource = setupParamSourceForOutOfBusiness(outOfBusiness);
        GeneratedKeyHolder keyHolder = insertOutOfBusinessCredentials(parameterSource);
        Integer generatedId = JdbcUtil.getIdFromKeyHolder(keyHolder);
        outOfBusiness.setId(generatedId);

        return outOfBusiness;
    }

    @Override
    public OutOfBusiness update(OutOfBusiness outOfBusiness) {
        updateOutOfBusinessCredentials(outOfBusiness, setupParamSourceForOutOfBusiness(outOfBusiness));

        return outOfBusiness;
    }

    private void updateOutOfBusinessCredentials(OutOfBusiness outOfBusiness, MapSqlParameterSource parameterSource) {
        parameterSource.addValue("out_of_business_id", outOfBusiness.getId(), INTEGER);

        String query = "UPDATE out_of_business "
                + "SET cause = :cause, start = :start, finish = :finish,  "
                + "barbershop_id = :barbershop_id "
                + "WHERE id = :out_of_business_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void deleteById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("out_of_business_id", id, INTEGER);

        String query = "DELETE FROM out_of_business WHERE id = :out_of_business_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public boolean existsById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("out_of_business_id", id, INTEGER);

        String query = "SELECT EXISTS(SELECT * FROM out_of_business WHERE id = :out_of_business_id)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    @Override
    public List<OutOfBusiness> findByBarbershopName(String barbershopName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_name", barbershopName, VARCHAR);

        String query = "SELECT oob.id AS id, oob.cause AS cause, "
                + "oob.start AS start, oob.finish AS finish, oob.barbershop_id AS barbershop_id "
                + "FROM out_of_business oob "
                + "JOIN barbershops b on b.id = oob.barbershop_id "
                + "WHERE b.name = :barbershop_name";

        return jdbcTemplate.query(query, parameterSource, outOfBusinessRowMapper);
    }

    private MapSqlParameterSource setupParamSourceForOutOfBusiness(OutOfBusiness outOfBusiness) {
        return new MapSqlParameterSource()
                .addValue("cause", outOfBusiness.getCause(), VARCHAR)
                .addValue("start", outOfBusiness.getStart(), TIMESTAMP)
                .addValue("finish", outOfBusiness.getFinish(), TIMESTAMP)
                .addValue("barbershop_id", outOfBusiness.getBarbershopId(), INTEGER);
    }

    private GeneratedKeyHolder insertOutOfBusinessCredentials(MapSqlParameterSource parameterSource) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO out_of_business(cause, start, finish, barbershop_id) "
                + "VALUES (:cause, :start, :finish, :barbershop_id)";

        jdbcTemplate.update(query, parameterSource, keyHolder, new String[]{"id"});

        return keyHolder;
    }

}
