package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_RELATION_QUERY = "INSERT INTO user_profile "
            + "VALUES (:user_id, :profile_id)";

    @Override
    public void saveRelationByUser(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", user.getId())
                .addValue("profile_id", user.getProfileId());

        jdbcTemplate.update(INSERT_RELATION_QUERY, parameterSource);
    }

    @Override
    public void saveRelationByProfile(Profile profile) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", profile.getUserId())
                .addValue("profile_id", profile.getId());

        jdbcTemplate.update(INSERT_RELATION_QUERY, parameterSource);
    }

    @Override
    public void deleteRelationByUserId(Integer userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", userId);

        String query = "DELETE FROM user_profile WHERE user_id = :user_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void deleteRelationByProfileId(Integer profileId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("profile_id", profileId);

        String query = "DELETE FROM user_profile WHERE profile_id = :profile_id";

        jdbcTemplate.update(query, parameterSource);
    }

}
