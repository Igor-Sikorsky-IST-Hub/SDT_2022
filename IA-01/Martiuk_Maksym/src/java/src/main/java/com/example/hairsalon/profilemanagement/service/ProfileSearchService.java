package com.example.hairsalon.profilemanagement.service;

import com.example.hairsalon.entity.Profile;

import java.util.List;

public interface ProfileSearchService {

    Profile getProfileById(Integer profileId);

    List<Profile> getAllProfiles();

    Profile getProfileByUserId(Integer userId);
}
