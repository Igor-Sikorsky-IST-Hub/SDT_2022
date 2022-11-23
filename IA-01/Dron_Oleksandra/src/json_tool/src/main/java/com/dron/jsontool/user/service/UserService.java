package com.dron.jsontool.user.service;

import com.dron.jsontool.user.repository.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

	User save(User user);

	User findById(UUID id);

	User findByEmail(String email);

    List<User> findAll();
}
