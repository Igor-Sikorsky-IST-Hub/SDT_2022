package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Profile;
import com.example.hairsalon.entity.User;

public interface UserProfileRepository {

    void saveRelationByUser(User user);

    void saveRelationByProfile(Profile profile);

    void deleteRelationByUserId(Integer userId);

    void deleteRelationByProfileId(Integer profileId);

}
