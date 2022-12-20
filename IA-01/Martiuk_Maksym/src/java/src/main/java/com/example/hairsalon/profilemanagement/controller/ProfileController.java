package com.example.hairsalon.profilemanagement.controller;

import com.example.hairsalon.profilemanagement.converter.ProfileResponseDtoConverter;
import com.example.hairsalon.profilemanagement.dto.ProfileRequestDto;
import com.example.hairsalon.profilemanagement.dto.ProfileResponseDto;
import com.example.hairsalon.profilemanagement.service.ProfileCreationService;
import com.example.hairsalon.profilemanagement.service.ProfileSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileCreationService profileCreationService;
    private final ProfileSearchService profileSearchService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @GetMapping("/{profileId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProfileResponseDto getProfile(@PathVariable("profileId") Integer profileId) {
        var profile = profileSearchService.getProfileById(profileId);

        return ProfileResponseDtoConverter.toResponseDto(profile);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')"
            + "or ( hasAuthority('CLIENT') and authentication.principal.userId.equals(userId) )")
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProfileResponseDto getProfileByUserId(@PathVariable("userId") Integer userId) {
        var profile = profileSearchService.getProfileByUserId(userId);

        return ProfileResponseDtoConverter.toResponseDto(profile);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ProfileResponseDto> getProfiles() {
        var profileList = profileSearchService.getAllProfiles();

        return ProfileResponseDtoConverter.toResponseDtoList(profileList);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ProfileResponseDto createProfile(@Valid @RequestBody ProfileRequestDto dto) {
        var profile = profileCreationService.createProfile(dto);

        return ProfileResponseDtoConverter.toResponseDto(profile);
    }

    @PreAuthorize("(hasAuthority('CLIENT') and authentication.principal.userId.equals(#dto.userId)) "
            + "or hasAuthority('ADMIN')")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProfileResponseDto updateProfile(@Valid @RequestBody ProfileRequestDto dto) {
        var profile = profileCreationService.updateProfile(dto);

        return ProfileResponseDtoConverter.toResponseDto(profile);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BARBERSHOP_ADMIN')")
    @DeleteMapping("/{profileId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProfileById(@PathVariable("profileId") Integer profileId) {
        profileCreationService.deleteProfile(profileId);
    }

}
