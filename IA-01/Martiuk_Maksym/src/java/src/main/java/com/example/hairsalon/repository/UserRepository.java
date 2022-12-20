package com.example.hairsalon.repository;

import com.example.hairsalon.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Integer id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    User update(User user);

    boolean existsById(Integer id);

    void deleteById(Integer id);

}
