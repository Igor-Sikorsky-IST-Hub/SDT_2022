package com.example.hairsalon.repository.rowmapper;

import com.example.hairsalon.entity.OutOfBusiness;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OutOfBusinessRowMapper implements RowMapper<OutOfBusiness> {

    @Override
    public OutOfBusiness mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer outOfBusinessId = rs.getObject("id", Integer.class);
        String cause = rs.getString("cause");
        LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
        LocalDateTime finish = rs.getTimestamp("finish").toLocalDateTime();
        Integer barbershopId = rs.getObject("barbershop_id", Integer.class);

        return OutOfBusiness.builder()
                .id(outOfBusinessId)
                .cause(cause)
                .start(start)
                .finish(finish)
                .barbershopId(barbershopId)
                .build();
    }

}
