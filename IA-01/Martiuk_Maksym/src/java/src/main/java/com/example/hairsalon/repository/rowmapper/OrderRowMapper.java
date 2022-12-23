package com.example.hairsalon.repository.rowmapper;

import com.example.hairsalon.entity.Order;
import com.example.hairsalon.entity.OrderStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer orderId = rs.getObject("id", Integer.class);
        LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
        LocalDateTime finish = rs.getTimestamp("finish").toLocalDateTime();
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("status"));
        Integer clientId = rs.getObject("client_id", Integer.class);
        Integer barberId = rs.getObject("barber_id", Integer.class);
        Integer barbershopId = rs.getObject("barbershop_id", Integer.class);

        return Order.builder()
                .id(orderId)
                .start(start)
                .finish(finish)
                .status(orderStatus)
                .clientProfileId(clientId)
                .barberProfileId(barberId)
                .barbershopId(barbershopId)
                .build();
    }
}
