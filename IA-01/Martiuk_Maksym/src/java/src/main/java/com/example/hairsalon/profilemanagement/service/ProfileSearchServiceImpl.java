package com.example.hairsalon.profilemanagement.service;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileSearchServiceImpl implements ProfileSearchService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile getProfileById(Integer profileId) {
        return profileRepository.findById(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Cannot find profile with id: %d", profileId))
        );
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getProfileByUserId(Integer userId) {
        return profileRepository.findAll().stream()
                .filter(profile -> profile.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("Cannot find profile for user with id: %d", userId)
                        )
                );
    }

}
