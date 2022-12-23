package com.example.hairsalon.repository.rowmapper;

import com.example.hairsalon.entity.Barbershop;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BarbershopRowMapper implements RowMapper<Barbershop> {

    @Override
    public Barbershop mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer barbershopId = rs.getObject("id", Integer.class);
        String name = rs.getString("name");
        String city = rs.getString("city");
        Integer amountOfWorkplaces = rs.getObject("amount_of_workplaces", Integer.class);

        return Barbershop.builder()
                .id(barbershopId)
                .name(name)
                .city(city)
                .amountOfWorkplaces(amountOfWorkplaces)
                .build();
    }

}
