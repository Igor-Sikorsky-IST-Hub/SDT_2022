package com.example.hairsalon.profileavatar.service;

import com.amazonaws.util.IOUtils;
import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.profileavatar.util.FileTypeUtility;
import com.example.hairsalon.exception.ResourceWriteException;
import com.example.hairsalon.repository.ProfileRepository;
import com.example.hairsalon.s3storage.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class S3ProfileAvatarService implements ProfileAvatarService {

    private final ObjectStorageService objectStorageService;
    private final ProfileRepository profileRepository;

    @Value("${avatars.bucket-name}")
    private String avatarsBucketName;

    @Override
    public void uploadAvatar(MultipartFile file, Integer profileId) {
        if (file.isEmpty()) {
            throw new ResourceWriteException("File cannot be empty");
        }

        final var fileName = file.getOriginalFilename();
        final var objectName = UUID.randomUUID().toString().replaceAll("-", "")
                + requireNonNull(fileName).substring(fileName.lastIndexOf("."));

        if (isNull(FileTypeUtility.getFileType(file))) {
            throw new ResourceWriteException("Can write only jpg or png");
        }

        var profile = extractProfile(profileId);
        profile.setAvatar(objectName);
        profileRepository.save(profile);

        objectStorageService.uploadFile(file, avatarsBucketName, objectName);
    }

    @Override
    public byte[] downloadAvatar(Integer profileId) {
        var profile = extractProfile(profileId);
        var avatar = objectStorageService.downloadFile(avatarsBucketName, profile.getAvatar());

        try {
            return IOUtils.toByteArray(avatar.getObjectContent());
        } catch (IOException exception) {
            throw new ResourceNotFoundException(
                    String.format("Cannot find profile(id: %d) avatar", profileId)
            );
        }
    }

    @Override
    public void deleteAvatar(Integer profileId) {
        var profile = extractProfile(profileId);
        objectStorageService.removeObject(avatarsBucketName, profile.getAvatar());
    }

    @Override
    public void updateAvatar(MultipartFile file, Integer profileId) {
        objectStorageService.removeObject(avatarsBucketName, String.valueOf(profileId));
        uploadAvatar(file, profileId);
    }

    private Profile extractProfile(Integer profileId) {
        return profileRepository.findById(profileId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Cannot find profile with id: %d", profileId))
        );
    }

}
