package com.example.hairsalon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ActivationCodeRepositoryImpl implements ActivationCodeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public static final String QUERY_FOR_PROFILE_ID = "SELECT pac.profile_id FROM profile_activation_code pac "
            + "WHERE pac.activation_code = :code";

    @Override
    public Integer findByActivationCode(String code) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("code", code);
        try {
            return jdbcTemplate.queryForObject(QUERY_FOR_PROFILE_ID, parameterSource, Integer.class);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

}
