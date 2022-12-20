package com.example.hairsalon.profileavatar.controller.rest;

import com.example.hairsalon.profileavatar.service.ProfileAvatarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/avatars")
@RequiredArgsConstructor
@Slf4j
public class ProfileAvatarController {

    private final ProfileAvatarService profileAvatarService;

    @GetMapping(
            value = "/{profileId}",
            produces = {
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE
            }
    )
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable("profileId") Integer profileId) {
        final var avatar = profileAvatarService.downloadAvatar(profileId);
        return ResponseEntity.ok(avatar);
    }

    @PostMapping("/{profileId}")
    public ResponseEntity<String> uploadFile(@PathVariable("profileId") Integer profileId, MultipartFile file) {
        profileAvatarService.uploadAvatar(file, profileId);
        return ResponseEntity.ok("Uploaded!");
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<String> updateAvatar(@PathVariable("profileId") Integer profileId, MultipartFile file) {
        profileAvatarService.updateAvatar(file, profileId);
        return ResponseEntity.ok("Update avatar!");
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> deleteAvatar(@PathVariable("profileId") Integer profileId) {
        profileAvatarService.deleteAvatar(profileId);
        return ResponseEntity.ok("Delete avatar!");
    }
}
