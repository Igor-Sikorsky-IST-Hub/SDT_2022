package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Barbershop;

import java.util.List;
import java.util.Optional;

public interface BarbershopRepository {

    Optional<Barbershop> findById(Integer id);

    Optional<Barbershop> findByName(String name);

    List<Barbershop> findAll();

    List<Barbershop> findAllByIdList(List<Integer> barbershopIds);

    Barbershop save(Barbershop barbershop);

    Barbershop update(Barbershop barbershop);

    void deleteById(Integer id);

    boolean existsById(Integer id);

}
