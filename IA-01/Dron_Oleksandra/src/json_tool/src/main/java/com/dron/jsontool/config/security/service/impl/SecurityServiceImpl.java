package com.dron.jsontool.config.security.service.impl;

import com.dron.jsontool.config.security.service.SecurityService;
import com.dron.jsontool.config.security.user.UserPrincipal;
import com.dron.jsontool.user.repository.entity.User;
import com.dron.jsontool.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SecurityServiceImpl implements SecurityService {

	UserService userService;

	@Override
	public User getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}

		if (authentication != null) {
			UserPrincipal user = (UserPrincipal) authentication
					.getPrincipal();

			return userService.findById(user.getId());
		}

		return null;
	}
}
