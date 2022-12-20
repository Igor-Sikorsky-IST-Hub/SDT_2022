package com.example.hairsalon.email.service;

import com.example.hairsalon.entity.Profile;

public interface EmailActivationSenderService {

    void sendActivationMessage(Profile profile, String activationCode);

}
