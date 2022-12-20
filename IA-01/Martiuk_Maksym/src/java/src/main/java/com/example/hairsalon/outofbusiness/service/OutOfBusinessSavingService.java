package com.example.hairsalon.outofbusiness.service;

import com.example.hairsalon.entity.OutOfBusiness;
import com.example.hairsalon.outofbusiness.dto.OutOfBusinessRequestDto;

public interface OutOfBusinessSavingService {

    OutOfBusiness saveToOutOfBusinessCategory(OutOfBusinessRequestDto dto);

    OutOfBusiness updateOutOfBusinessCategory(OutOfBusinessRequestDto dto);

}
