package com.example.hairsalon.outofbusiness.service;

import com.example.hairsalon.entity.OutOfBusiness;

import java.util.List;

public interface OutOfBusinessCheckService {

    List<OutOfBusiness> getAllFromOutOfBusinessCategory();

    OutOfBusiness getOutOfBusinessInfoIfInThisCategory(String barbershopName);

}
