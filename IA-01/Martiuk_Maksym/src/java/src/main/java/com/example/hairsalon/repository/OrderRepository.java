package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Integer id);

    List<Order> findAll();

    Order save(Order order);

    Order update(Order order);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    void deleteAllInBatch(List<Order> ordersForDeleting);
}
