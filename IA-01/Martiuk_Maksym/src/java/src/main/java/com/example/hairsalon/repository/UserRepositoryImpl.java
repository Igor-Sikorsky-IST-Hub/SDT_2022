package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Role;
import com.example.hairsalon.entity.User;
import com.example.hairsalon.repository.extractor.UserExtractor;
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
import java.util.Set;

import static java.sql.Types.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserExtractor userExtractor;

    @Override
    public Optional<User> findById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", id);

        String query = "SELECT u.id, u.username, u.password, "
                + "up.profile_id, u.active,  ur.role "
                + "FROM users u "
                + "LEFT JOIN user_roles ur ON u.id = ur.user_id "
                + "LEFT JOIN user_profile up on u.id = up.user_id "
                + "WHERE u.id = :user_id";

        try {
            return requireNonNull(jdbcTemplate.query(query, parameterSource, userExtractor)).stream()
                    .findFirst();
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for user id: {}", exception.getClass().getName(), id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("username", username);

        String query = "SELECT u.id, u.username, u.password, "
                + "up.profile_id, u.active, ur.role "
                + "FROM users u "
                + "LEFT JOIN user_roles ur ON u.id = ur.user_id "
                + "LEFT JOIN user_profile up on u.id = up.user_id "
                + "WHERE u.username = :username";

        try {
            return requireNonNull(jdbcTemplate.query(query, parameterSource, userExtractor)).stream()
                    .findFirst();
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for username: {}", exception.getClass().getName(), username);
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT u.id, u.username, u.password, "
                + "up.profile_id, u.active, ur.role "
                + "FROM users u "
                + "LEFT JOIN user_roles ur ON u.id = ur.user_id "
                + "LEFT JOIN user_profile up on u.id = up.user_id ";

        return jdbcTemplate.query(query, userExtractor);
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource parameterSource = setParamSourceForUserCredits(user);
        GeneratedKeyHolder keyHolder = insertUserCredentials(parameterSource);
        Integer generatedId = JdbcUtil.getIdFromKeyHolder(keyHolder);
        user.setId(generatedId);
        setUserRoles(user);

        return user;
    }

    @Override
    public User update(User user) {
        updateUserCredentials(user, setParamSourceForUserCredits(user));
        deleteUserRoles(user);
        setUserRoles(user);

        return user;
    }

    @Override
    public boolean existsById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        String sql = "SELECT EXISTS(SELECT * FROM users WHERE id = :id)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, parameterSource, Boolean.class));
    }

    @Override
    public void deleteById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", id);

        String query = "DELETE FROM users WHERE id = :user_id";

        jdbcTemplate.update(query, parameterSource);
    }

    private MapSqlParameterSource setParamSourceForUserCredits(User user) {
        return new MapSqlParameterSource()
                .addValue("active", user.isActive(), BOOLEAN)
                .addValue("username", user.getUsername(), VARCHAR)
                .addValue("password", user.getPassword(), VARCHAR);
    }

    private GeneratedKeyHolder insertUserCredentials(MapSqlParameterSource parameterSource) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO users(active, username, password) "
                + "VALUES (:active, :username, :password)";

        jdbcTemplate.update(query, parameterSource, keyHolder, new String[]{"id"});

        return keyHolder;
    }

    private void setUserRoles(User user) {
        if (user.getRoles().isEmpty()) {
            return;
        }

        Set<Role> userRoles = user.getRoles();

        String query = "INSERT INTO user_roles(user_id, role) VALUES (:id, :role)";

        jdbcTemplate.batchUpdate(query, createBatchArgs(userRoles, user.getId()));
    }

    private MapSqlParameterSource[] createBatchArgs(Set<Role> part, Integer id) {
        return part.stream()
                .map(role -> paramSourceForUserRoles(role, id))
                .toArray(MapSqlParameterSource[]::new);

    }

    private MapSqlParameterSource paramSourceForUserRoles(Role role, Integer userId) {
        return new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("role", role.name());
    }

    private void updateUserCredentials(User user, MapSqlParameterSource parameterSource) {
        parameterSource.addValue("id", user.getId());

        String query = "UPDATE users "
                + "SET active = :active, username = :username, password = :password "
                + "WHERE id = :id";

        jdbcTemplate.update(query, parameterSource);
    }

    private void deleteUserRoles(User user) {
        MapSqlParameterSource parameterSource =
                new MapSqlParameterSource("user_id", user.getId());

        String query = "DELETE FROM user_roles WHERE user_id = :user_id";

        jdbcTemplate.update(query, parameterSource);
    }

}
