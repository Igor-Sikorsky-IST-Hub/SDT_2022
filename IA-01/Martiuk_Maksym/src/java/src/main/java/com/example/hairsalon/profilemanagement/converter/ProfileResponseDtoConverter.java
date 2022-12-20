package com.example.hairsalon.profilemanagement.converter;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.profilemanagement.dto.ProfileResponseDto;

import java.util.List;

public final class ProfileResponseDtoConverter {

    private ProfileResponseDtoConverter() {
    }

    public static ProfileResponseDto toResponseDto(Profile profile) {
        var responseDto = new ProfileResponseDto();
        responseDto.setProfileId(profile.getId());
        responseDto.setEmail(profile.getEmail());
        responseDto.setName(profile.getName());
        responseDto.setSurname(profile.getSurname());

        return responseDto;
    }

    public static List<ProfileResponseDto> toResponseDtoList(List<Profile> profiles) {
        return profiles.stream()
                .map(ProfileResponseDtoConverter::toResponseDto)
                .toList();
    }

}
