package com.example.hairsalon.barbershopmanagement.service;

import com.example.hairsalon.entity.Role;
import com.example.hairsalon.entity.User;
import com.example.hairsalon.exception.PermissionDeniedException;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BarbershopPermissionCheckServiceImpl implements BarbershopPermissionCheckService {

    private final BarbershopShowService barbershopShowService;
    private final UserRepository userRepository;

    @Override
    public void checkForPlatformAdminRoleOrBarbershopAdmin(Integer barbershopId) {
        var principalUsername = getPrincipalUsername();
        var principal = getUser(principalUsername);
        var barbershopAdminIdList = barbershopShowService.getBarbershopAdminIdList(barbershopId);

        checkThatPrincipalIsABarbershopAdminOrPlatformAdmin(principal, barbershopAdminIdList);
    }

    private String getPrincipalUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(String.format(
                        "Cannot find user with username: %s",
                        username
                ))
        );
    }

    private void checkThatPrincipalIsABarbershopAdminOrPlatformAdmin(
            User principal,
            List<Integer> barbershopAdminIdList
    ) {
        if (!barbershopAdminIdList.contains(principal.getId())
                || !principal.getRoles().contains(Role.ADMIN)) {
            throw new PermissionDeniedException(String.format(
                    "User with username: %s, doesn't have access to this operation",
                    principal.getUsername()
            ));
        }
    }

}
