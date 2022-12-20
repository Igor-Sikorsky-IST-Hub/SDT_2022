package com.dron.jsontool.config.migrations;

import com.dron.jsontool.user.repository.UserRepository;
import com.dron.jsontool.user.repository.entity.User;
import com.dron.jsontool.user.repository.entity.enumeration.Role;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DBMigrations implements CommandLineRunner {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) { // insert default user and admin
		String userEmail = "user@gmail.com";
		String adminEmail = "admin@gmail.com";
		String password = "password";

		userRepository.findByEmail(userEmail)
				.orElseGet(() -> userRepository.save(User.builder()
						.role(Role.ROLE_USER)
						.firstname("User")
						.lastname("User")
						.email(userEmail)
						.password(passwordEncoder.encode(password))
						.build()));

		userRepository.findByEmail(adminEmail)
				.orElseGet(() -> userRepository.save(User.builder()
						.role(Role.ROLE_ADMIN)
						.firstname("Admin")
						.lastname("Admin")
						.email(adminEmail)
						.password(passwordEncoder.encode(password))
						.build()));

	}
}