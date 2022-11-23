package com.dron.jsontool.user.service.impl;

import com.dron.jsontool.common.exception.NotFoundException;
import com.dron.jsontool.jsonshema.repositiry.JsonSchemaRepository;
import com.dron.jsontool.user.repository.UserRepository;
import com.dron.jsontool.user.repository.entity.User;
import com.dron.jsontool.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	JsonSchemaRepository jsonSchemaRepository;

	@Override
	@Transactional
	public User save(User user) {
		User saved = userRepository.save(user);

		if (CollectionUtils.isNotEmpty(user.getJsonSchemas())) {
			user.getJsonSchemas().forEach(schema -> schema.setOwner(saved));
			saved.setJsonSchemas(jsonSchemaRepository.saveAll(user.getJsonSchemas()));
		}

		return saved;
	}

	@Override
	public User findById(UUID id) {
		return userRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found by id"));
	}

	@Override
	public User findByEmail(String email) {
		return userRepository
				.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User not found by email"));
	}

    @Override
    public List<User> findAll() {
		return userRepository.findAll();
    }
}
