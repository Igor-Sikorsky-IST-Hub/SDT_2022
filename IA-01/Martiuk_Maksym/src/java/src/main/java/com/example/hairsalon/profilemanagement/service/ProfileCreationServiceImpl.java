package com.example.hairsalon.profilemanagement.service;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.profilemanagement.dto.ProfileRequestDto;
import com.example.hairsalon.repository.ProfileRepository;
import com.example.hairsalon.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileCreationServiceImpl implements ProfileCreationService {

    private final ProfileRepository profileRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public Profile createProfile(ProfileRequestDto dto) {
        var profile = Profile.builder()
                .id(dto.getProfileId())
                .email(dto.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .userId(dto.getUserId())
                .build();

        if (nonNull(profile.getUserId()) && profile.getUserId() > 0) {
            userProfileRepository.saveRelationByProfile(profile);
        }

        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(ProfileRequestDto dto) {
        var profile = Profile.builder()
                .id(dto.getProfileId())
                .email(dto.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .userId(dto.getUserId())
                .build();

        return profileRepository.update(profile);
    }

    @Override
    public void deleteProfile(Integer profileId) {
        if (!profileRepository.existsById(profileId)) {
            throw new ResourceNotFoundException(String.format("Cannot find profile with id %d: ", profileId));
        }

        profileRepository.deleteById(profileId);
        userProfileRepository.deleteRelationByProfileId(profileId);
    }

}
