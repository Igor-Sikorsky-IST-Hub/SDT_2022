package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.repository.rowmapper.OrderRowMapper;
import com.example.hairsalon.repository.util.JdbcUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.sql.Types.*;
import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;

    private static final int PARTITION_SIZE = 300;

    @Override
    public Optional<Order> findById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_id", id, INTEGER);

        String query = "SELECT o.id AS id, o.start AS start, "
                + "o.finish AS finish, o.status AS status, o.client_id AS client_id, "
                + "o.barber_id AS barber_id, o.barbershop_id as barbershop_id "
                + "FROM orders o "
                + "WHERE o.id = :order_id";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, parameterSource, orderRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Catch {} for user id: {}", exception.getClass().getName(), id);
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT o.id AS id, o.start AS start, "
                + "o.finish AS finish, o.status AS status, o.client_id AS client_id, "
                + "o.barber_id AS barber_id, o.barbershop_id as barbershop_id "
                + "FROM orders o ";

        return jdbcTemplate.query(query, orderRowMapper);
    }

    @Override
    public Order save(Order order) {
        MapSqlParameterSource parameterSource = setupParameterSource(order);

        GeneratedKeyHolder keyHolder = insertOrderCredentialsReturningKeyHolder(parameterSource);
        Integer generatedId = JdbcUtil.getIdFromKeyHolder(keyHolder);
        order.setId(generatedId);

        return order;
    }

    @Override
    public Order update(Order order) {
        MapSqlParameterSource parameterSource = setupParameterSource(order);

        updateOrderCredentials(order, parameterSource);

        return order;
    }

    @Override
    public boolean existsById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_id", id, INTEGER);

        String query = "SELECT EXISTS(SELECT * FROM orders WHERE id = :order_id)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameterSource, Boolean.class));
    }

    @Override
    public void deleteById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_id", id, INTEGER);

        String query = "DELETE FROM orders WHERE id = :order_id";

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public void deleteAllInBatch(List<Order> ordersForDeleting) {
        if (ordersForDeleting.isEmpty()) {
            return;
        }

        List<List<Order>> parts = Lists.partition(ordersForDeleting, PARTITION_SIZE);

        String query = "DELETE FROM orders WHERE id = :order_id";

        parts.forEach(part -> {
            List<Integer> listOfId = part.stream()
                    .map(Order::getId)
                    .toList();

            jdbcTemplate.batchUpdate(query, createBatchArgsForDelete(listOfId));
        });
    }

    private MapSqlParameterSource setupParameterSource(Order order) {
        return new MapSqlParameterSource()
                .addValue("start", order.getStart(), TIMESTAMP)
                .addValue("finish", order.getFinish(), TIMESTAMP)
                .addValue("status", order.getStatus(), VARCHAR)
                .addValue("client_id", order.getClientProfileId(), INTEGER)
                .addValue("barber_id", order.getBarberProfileId(), INTEGER)
                .addValue("barbershop_id", order.getBarbershopId(), INTEGER);
    }

    private GeneratedKeyHolder insertOrderCredentialsReturningKeyHolder(MapSqlParameterSource parameterSource) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO orders(start, finish, status, client_id, barber_id, barbershop_id) "
                + "VALUES (:start, :finish, :status, :client_id, :barber_id, :barbershop_id)";

        jdbcTemplate.update(query, parameterSource, keyHolder, new String[]{"id"});

        return keyHolder;
    }

    private void updateOrderCredentials(Order order, MapSqlParameterSource parameterSource) {
        if (nonNull(order.getId())) {
            parameterSource.addValue("order_id", order.getId(), INTEGER);
        }

        String query = "UPDATE orders "
                + "SET start = :start, finish = :finish, status = :status, "
                + "    client_id = :client_id, barber_id = :barber_id, barbershop_id = :barbershop_id "
                + "WHERE id = :order_id";

        jdbcTemplate.update(query, parameterSource);
    }

    private MapSqlParameterSource[] createBatchArgsForDelete(List<Integer> listOfOrdersId) {
        return listOfOrdersId.stream()
                .map(this::paramSourceForDeleteBatch)
                .toArray(MapSqlParameterSource[]::new);
    }

    private MapSqlParameterSource paramSourceForDeleteBatch(Integer orderId) {
        return new MapSqlParameterSource("order_id", orderId);
    }

}
