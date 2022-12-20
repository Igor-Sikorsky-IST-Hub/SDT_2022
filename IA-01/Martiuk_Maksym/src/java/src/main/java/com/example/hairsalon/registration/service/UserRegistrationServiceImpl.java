package com.example.hairsalon.registration.service;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.entity.Role;
import com.example.hairsalon.entity.User;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.registration.dto.UserRegistrationDto;
import com.example.hairsalon.repository.ProfileRepository;
import com.example.hairsalon.repository.UserProfileRepository;
import com.example.hairsalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.Objects.isNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public User registerUser(UserRegistrationDto dto) {
        var user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .active(true)
                .roles(Set.of(Role.CLIENT))
                .build();

        Profile savedProfile;

        if (isNull(dto.getProfileId()) || dto.getProfileId() < 1) {
            var profile = Profile.builder()
                    .name(dto.getName())
                    .surname(dto.getSurname())
                    .email(dto.getEmail())
                    .build();

            savedProfile = profileRepository.save(profile);
        } else {
            savedProfile = profileRepository.findById(dto.getProfileId()).orElseThrow(
                    () -> new ResourceNotFoundException(String.format(
                            "Cannot find profile with id: %d",
                            dto.getProfileId())
                    )
            );
        }


        var savedUser = userRepository.save(user);
        savedUser.setProfileId(savedProfile.getId());

        userProfileRepository.saveRelationByUser(savedUser);

        return savedUser;
    }

}
