package com.example.hairsalon.profileavatar.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileAvatarService {

    void uploadAvatar(MultipartFile file, Integer profileId);

    byte[] downloadAvatar(Integer profileId);

    void deleteAvatar(Integer profileId);

    void updateAvatar(MultipartFile file, Integer profileId);

}
