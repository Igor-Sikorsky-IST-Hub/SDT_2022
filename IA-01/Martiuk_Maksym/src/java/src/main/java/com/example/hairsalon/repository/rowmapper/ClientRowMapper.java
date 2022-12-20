package com.example.hairsalon.repository.rowmapper;

import com.example.hairsalon.entity.Client;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClientRowMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer barbershopId = rs.getObject("barbershop_id", Integer.class);
        Integer clientId = rs.getObject("client_id", Integer.class);

        return Client.builder()
                .barbershopId(barbershopId)
                .clientProfileId(clientId)
                .build();
    }

}
