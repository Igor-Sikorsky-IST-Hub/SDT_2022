package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    Optional<Profile> findById(Integer id);

    List<Profile> findAll();

    Profile save(Profile profile);

    Profile update(Profile profile);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<Profile> findAllByIdList(List<Integer> profileIdList);

    Optional<Profile> findByEmail(String email);
}
