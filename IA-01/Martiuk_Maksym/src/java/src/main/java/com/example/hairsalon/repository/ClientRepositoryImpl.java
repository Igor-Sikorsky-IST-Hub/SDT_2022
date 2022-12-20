package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Client;
import com.example.hairsalon.repository.rowmapper.ClientRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.sql.Types.INTEGER;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ClientRepositoryImpl implements ClientRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ClientRowMapper clientRowMapper;

    @Override
    public List<Client> findByBarbershopId(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_id", id, INTEGER);

        String query = "SELECT c.barbershop_id, c.client_id "
                + "FROM clients c "
                + "WHERE c.barbershop_id = :barbershop_id";

        return jdbcTemplate.query(query, parameterSource, clientRowMapper);
    }

    @Override
    public List<Client> findByClientProfileId(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("client_id", id, INTEGER);

        String query = "SELECT c.barbershop_id, c.client_id "
                + "FROM clients c "
                + "WHERE c.client_id = :client_id";

        return jdbcTemplate.query(query, parameterSource, clientRowMapper);
    }

    @Override
    public List<Client> findAll() {
        String query = "SELECT c.barbershop_id, c.client_id "
                + "FROM clients c";

        return jdbcTemplate.query(query, clientRowMapper);
    }

    @Override
    public Client save(Client client) {
        MapSqlParameterSource parameterSource = setupParamSourceForClients(client);

        String query = "INSERT INTO clients VALUES (:barbershop_id, :client_id)";

        jdbcTemplate.update(query, parameterSource);

        return client;
    }

    @Override
    public void deleteByBarbershopId(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_id", id, INTEGER);

        String query = "DELETE FROM clients WHERE barbershop_id = :barbershop_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void deleteByClientId(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("client_id", id, INTEGER);

        String query = "DELETE FROM clients WHERE client_id = :client_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void delete(Client client) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("barbershop_id", client.getBarbershopId(), INTEGER)
                .addValue("client_id", client.getClientProfileId(), INTEGER);

        String query = "DELETE FROM clients WHERE barbershop_id = :barbershop_id AND client_id = :client_id";

        jdbcTemplate.update(query, parameterSource);
    }

    private MapSqlParameterSource setupParamSourceForClients(Client client) {
        return new MapSqlParameterSource()
                .addValue("barbershop_id", client.getBarbershopId(), INTEGER)
                .addValue("client_id", client.getClientProfileId(), INTEGER);
    }

}
