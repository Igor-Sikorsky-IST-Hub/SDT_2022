package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.repository.rowmapper.ProfileRowMapper;
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

import static java.lang.Boolean.TRUE;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProfileRepositoryImpl implements ProfileRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProfileRowMapper profileRowMapper;

    @Override
    public Optional<Profile> findById(Integer id) {
        MapSqlParameterSource parameterSource = setupParamSourceForProfileId(id);

        String query = "SELECT p.id AS id, p.name AS name, p.surname AS surname, "
                + "p.email AS email, p.avatar AS avatar, u.id AS user_id "
                + "FROM profiles p "
                + "LEFT JOIN user_profile up on p.id = up.profile_id "
                + "LEFT JOIN users u on up.user_id = u.id "
                + "WHERE p.id = :profile_id";

        try {
            return ofNullable(jdbcTemplate.queryForObject(query, parameterSource, profileRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for profile with id: {}", exception.getClass().getName(), id);
            return Optional.empty();
        }
    }

    @Override
    public List<Profile> findAll() {
        String query = "SELECT p.id AS id, p.name AS name, p.surname AS surname, "
                + "p.email AS email, p.avatar AS avatar, u.id AS user_id "
                + "FROM profiles p "
                + "LEFT JOIN user_profile up on p.id = up.profile_id "
                + "LEFT JOIN users u on up.user_id = u.id ";

        return jdbcTemplate.query(query, profileRowMapper);
    }

    @Override
    public Profile save(Profile profile) {
        MapSqlParameterSource parameterSource = setupParamSourceForProfile(profile);
        GeneratedKeyHolder keyHolder = insertProfileCredentials(parameterSource);
        Integer generatedId = JdbcUtil.getIdFromKeyHolder(keyHolder);
        profile.setId(generatedId);

        return profile;
    }

    @Override
    public Profile update(Profile profile) {
        updateProfileCredentials(profile, setupParamSourceForProfile(profile));

        return profile;
    }

    @Override
    public void deleteById(Integer id) {
        MapSqlParameterSource parameterSource = setupParamSourceForProfileId(id);

        String query = "DELETE FROM profiles WHERE id = :profile_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public boolean existsById(Integer id) {
        MapSqlParameterSource parameterSource = setupParamSourceForProfileId(id);

        String query = "SELECT EXISTS(SELECT * FROM profiles WHERE id = :profile_id)";

        return TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    @Override
    public List<Profile> findAllByIdList(List<Integer> profileIdList) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("ids", profileIdList);

        String query = "SELECT p.id AS id, p.name AS name, p.surname AS surname, "
                + "p.email AS email, p.avatar AS avatar, u.id AS user_id "
                + "FROM profiles p "
                + "LEFT JOIN user_profile up on p.id = up.profile_id "
                + "LEFT JOIN users u on up.user_id = u.id "
                + "WHERE p.id IN (:ids)";

        return jdbcTemplate.query(query, parameterSource, profileRowMapper);
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("email", email);

        String query = "SELECT p.id AS id, p.name AS name, p.surname AS surname, "
                + "p.email AS email, p.avatar AS avatar, u.id AS user_id "
                + "FROM profiles p "
                + "LEFT JOIN user_profile up on p.id = up.profile_id "
                + "LEFT JOIN users u on up.user_id = u.id "
                + "WHERE p.email = :email";

        try {
            return ofNullable(jdbcTemplate.queryForObject(query, parameterSource, profileRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for profile with email: {}", exception.getClass().getName(), email);
            return Optional.empty();
        }
    }

    private MapSqlParameterSource setupParamSourceForProfileId(Integer id) {
        return new MapSqlParameterSource()
                .addValue("profile_id", id, INTEGER);
    }

    private MapSqlParameterSource setupParamSourceForProfile(Profile profile) {
        return new MapSqlParameterSource()
                .addValue("name", profile.getName(), VARCHAR)
                .addValue("surname", profile.getSurname(), VARCHAR)
                .addValue("email", profile.getEmail(), VARCHAR)
                .addValue("avatar", profile.getAvatar(), VARCHAR)
                .addValue("user_id", profile.getUserId(), INTEGER);
    }

    private GeneratedKeyHolder insertProfileCredentials(MapSqlParameterSource parameterSource) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO profiles(name, surname, email, avatar) "
                + "VALUES (:name, :surname, :email, :avatar)";

        jdbcTemplate.update(query, parameterSource, keyHolder, new String[]{"id"});

        return keyHolder;
    }

    private void updateProfileCredentials(Profile profile, MapSqlParameterSource parameterSource) {
        parameterSource.addValue("profile_id", profile.getId(), INTEGER);

        String query = "UPDATE profiles "
                + "SET name = :name, surname = :surname, email = :email, avatar = :avatar "
                + "WHERE id = :profile_id";

        jdbcTemplate.update(query, parameterSource);
    }

}
