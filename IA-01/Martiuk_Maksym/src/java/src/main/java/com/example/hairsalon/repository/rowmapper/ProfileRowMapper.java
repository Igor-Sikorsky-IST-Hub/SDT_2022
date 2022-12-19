package com.example.hairsalon.repository.rowmapper;

import com.example.hairsalon.entity.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer profileId = rs.getObject("id", Integer.class);
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String avatar = rs.getString("avatar");
        Integer userId = rs.getObject("user_id", Integer.class);

        return Profile.builder()
                .id(profileId)
                .name(name)
                .surname(surname)
                .email(email)
                .avatar(avatar)
                .userId(userId)
                .build();
    }

}
