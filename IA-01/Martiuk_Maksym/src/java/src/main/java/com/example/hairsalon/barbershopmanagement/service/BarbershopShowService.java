package com.example.hairsalon.barbershopmanagement.service;

import com.example.hairsalon.entity.Barbershop;
import com.example.hairsalon.entity.Client;
import com.example.hairsalon.entity.Employee;
import com.example.hairsalon.entity.OutOfBusiness;

import java.util.List;

public interface BarbershopShowService {

    List<Barbershop> showAllBarbershops();

    List<Client> showBarbershopClients(Integer barbershopId);

    List<Employee> showBarbershopEmployees(Integer barbershopId);

    List<Integer> getBarbershopAdminIdList(Integer barbershopId);

    List<Barbershop> showAllBarbershopsByOutOfBusinessList(List<OutOfBusiness> outOfBusinesses);

}
