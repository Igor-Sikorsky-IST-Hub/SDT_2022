 package com.example.hairsalon.repository.util;

import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Objects;

public final class JdbcUtil {

    private JdbcUtil() {
    }

    public static Integer getIdFromKeyHolder(GeneratedKeyHolder keyHolder) {
        Integer id;
        if (Objects.requireNonNull(keyHolder.getKeys()).size() > 1) {
            id = (Integer) keyHolder.getKeys().get("id");
        } else {
            id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        }

        return id;
    }

}
