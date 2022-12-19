package com.example.hairsalon.profilemanagement.service;

import com.example.hairsalon.repository.ActivationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
@Transactional
@RequiredArgsConstructor
public class MailVerificationServiceImpl implements MailVerificationService {

    private final ActivationCodeRepository activationCodeRepository;

    @Override
    public boolean activateProfile(String code) {
        Integer profileId = activationCodeRepository.findByActivationCode(code);

        if (isNull(profileId)) {
            return false;
        }

        //todo activateProfile method

        return false;
    }

}
