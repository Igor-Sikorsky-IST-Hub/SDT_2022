package com.dron.jsontool.common.security.user;

import com.dron.jsontool.common.exception.NotFoundException;
import com.dron.jsontool.user.repository.UserRepository;
import com.dron.jsontool.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserPrincipalDetailsService implements UserDetailsService {

	UserRepository userRepository;

	@Override
	public UserPrincipal loadUserByUsername(String s) throws NotFoundException {
		User user = userRepository
				.findByEmail(s)
				.orElseThrow(() -> new NotFoundException("User not found"));

		return new UserPrincipal(user);
	}

}