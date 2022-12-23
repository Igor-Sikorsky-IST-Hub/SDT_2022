package com.example.hairsalon.repository.extractor;

import com.example.hairsalon.entity.Role;
import com.example.hairsalon.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
public class UserExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, User> data = new LinkedHashMap<>();

        while (rs.next()) {
            Integer userId = rs.getObject("id", Integer.class);

            if (nonNull(userId)) {
                User user = extractUserCredits(rs, userId);
                data.putIfAbsent(userId, user);


                String role = rs.getString("role");
                if (nonNull(role)) {
                    data.get(userId).getRoles().add(Role.valueOf(role));
                }
            }
        }

        return data.values().stream()
                .toList();
    }

    static User extractUserCredits(ResultSet rs, int userId) throws SQLException {
        String username = rs.getString("username");
        String password = rs.getString("password");
        boolean active = rs.getBoolean("active");
        Integer profileId = rs.getObject("profile_id", Integer.class);

        return User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .active(active)
                .profileId(profileId)
                .roles(new HashSet<>())
                .build();
    }

}
