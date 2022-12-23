package com.example.hairsalon.profilemanagement.service;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.profilemanagement.dto.ProfileRequestDto;

public interface ProfileCreationService {

    Profile createProfile(ProfileRequestDto dto);

    Profile updateProfile(ProfileRequestDto dto);

    void deleteProfile(Integer profileId);

}
