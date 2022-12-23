package com.example.hairsalon.repository;

import com.example.hairsalon.entity.Client;
import java.util.List;

public interface ClientRepository {

    List<Client> findByBarbershopId(Integer id);

    List<Client> findByClientProfileId(Integer id);

    List<Client> findAll();

    Client save(Client client);

    void deleteByBarbershopId(Integer id);

    void deleteByClientId(Integer id);

    void delete(Client client);

}
