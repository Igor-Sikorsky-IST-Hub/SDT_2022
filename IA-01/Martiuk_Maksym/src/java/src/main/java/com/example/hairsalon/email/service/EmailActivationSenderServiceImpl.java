package com.example.hairsalon.email.service;

import com.example.hairsalon.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailActivationSenderServiceImpl implements EmailActivationSenderService {

    private final JavaMailSender mailSender;

    private static final String FROM_MAIL = "no-reply@my-barber.com";

    @Override
    public void sendActivationMessage(Profile profile, String activationCode) {
        if (!profile.getEmail().isEmpty()) {
            var message = String.format(
                    "Hello, %s! Visit next link to activate profile: "
                            + "http://localhost:8028/activate-profile/%s",
                    profile.getName(),
                    activationCode
            );

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(FROM_MAIL);
            mailMessage.setTo(profile.getEmail());
            mailMessage.setSubject("Activate profile");
            mailMessage.setText(message);

            mailSender.send(mailMessage);
        }
    }

}
