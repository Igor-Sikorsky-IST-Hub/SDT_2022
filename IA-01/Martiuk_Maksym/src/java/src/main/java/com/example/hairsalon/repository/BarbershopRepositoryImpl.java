package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Barbershop;
import com.example.hairsalon.repository.rowmapper.BarbershopRowMapper;
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

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BarbershopRepositoryImpl implements BarbershopRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BarbershopRowMapper barbershopRowMapper;

    @Override
    public Optional<Barbershop> findById(Integer id) {
        MapSqlParameterSource parameterSource = getParamSourceForBarbershopId(id);

        String query = "SELECT b.id AS id, b.name AS name, b.city AS city, "
                + "b.amount_of_workplaces AS amount_of_workplaces "
                + "FROM barbershops b "
                + "WHERE b.id = :barbershop_id";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, parameterSource, barbershopRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for barbershop id: {}", exception.getClass().getName(), id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Barbershop> findByName(String name) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_name", name, VARCHAR);

        String query = "SELECT b.id AS id, b.name AS name, b.city AS city, "
                + "b.amount_of_workplaces AS amount_of_workplaces "
                + "FROM barbershops b "
                + "WHERE b.name = :barbershop_name";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, parameterSource, barbershopRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for barbershop name: {}", exception.getClass().getName(), name);
            return Optional.empty();
        }
    }

    @Override
    public List<Barbershop> findAll() {
        String query = "SELECT b.id AS id, b.name AS name, b.city AS city, "
                + "b.amount_of_workplaces AS amount_of_workplaces "
                + "FROM barbershops b";

        return jdbcTemplate.query(query, barbershopRowMapper);
    }

    @Override
    public List<Barbershop> findAllByIdList(List<Integer> barbershopIds) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("ids", barbershopIds);

        String query = "SELECT DISTINCT b.id AS id, b.name AS name, b.city AS city, "
                + "b.amount_of_workplaces AS amount_of_workplaces "
                + "FROM barbershops b "
                + "WHERE b.id IN (:ids)";

        return jdbcTemplate.query(query, parameterSource, barbershopRowMapper);
    }

    @Override
    public Barbershop save(Barbershop barbershop) {
        MapSqlParameterSource parameterSource = setupParamSourceForBarbershopCredentials(barbershop);
        GeneratedKeyHolder keyHolder = insertBarbershopCredentials(parameterSource);
        Integer generatedId = JdbcUtil.getIdFromKeyHolder(keyHolder);
        barbershop.setId(generatedId);

        return barbershop;
    }

    @Override
    public Barbershop update(Barbershop barbershop) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        MapSqlParameterSource parameterSource = getParamSourceForBarbershopId(id);

        String query = "DELETE FROM barbershops WHERE id = :barbershop_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public boolean existsById(Integer id) {
        MapSqlParameterSource parameterSource = getParamSourceForBarbershopId(id);

        String query = "SELECT EXISTS(SELECT * FROM barbershops WHERE id = :barbershop_id)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    private MapSqlParameterSource getParamSourceForBarbershopId(Integer id) {
        return new MapSqlParameterSource()
                .addValue("barbershop_id", id, INTEGER);
    }

    private GeneratedKeyHolder insertBarbershopCredentials(MapSqlParameterSource parameterSource) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO barbershops(name, city, amount_of_workplaces) "
                + "VALUES (:name, :city, :amount_of_workplaces)";

        jdbcTemplate.update(query, parameterSource, keyHolder, new String[]{"id"});

        return keyHolder;
    }

    private MapSqlParameterSource setupParamSourceForBarbershopCredentials(Barbershop barbershop) {
        return new MapSqlParameterSource()
                .addValue("name", barbershop.getName(), VARCHAR)
                .addValue("city", barbershop.getCity(), VARCHAR)
                .addValue("amount_of_workplaces", barbershop.getAmountOfWorkplaces(), INTEGER);
    }

}
