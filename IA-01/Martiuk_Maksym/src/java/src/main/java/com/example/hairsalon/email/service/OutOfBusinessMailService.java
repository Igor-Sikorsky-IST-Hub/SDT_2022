package com.example.hairsalon.email.service;

import com.example.hairsalon.email.dto.OutOfBusinessMailNotificationDto;

public interface OutOfBusinessMailService {

    int sendMailNotificationAboutOutOfBusiness(OutOfBusinessMailNotificationDto dto);

    int sendMailNotificationAboutUpdatedOutOfBusiness(OutOfBusinessMailNotificationDto dto);

}
