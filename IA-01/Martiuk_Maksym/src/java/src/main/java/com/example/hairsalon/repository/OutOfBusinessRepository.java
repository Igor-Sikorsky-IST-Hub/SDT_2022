package com.example.hairsalon.repository;

import com.example.hairsalon.entity.OutOfBusiness;

import java.util.List;
import java.util.Optional;

public interface OutOfBusinessRepository {

    Optional<OutOfBusiness> findById(Integer id);

    List<OutOfBusiness> findByBarbershopId(Integer barbershopId);

    List<OutOfBusiness> findAll();

    OutOfBusiness save(OutOfBusiness outOfBusiness);

    OutOfBusiness update(OutOfBusiness outOfBusiness);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<OutOfBusiness> findByBarbershopName(String barbershopName);
}
