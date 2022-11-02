package com.example.hairsalon.email.service;

import com.example.hairsalon.email.aspect.MonitorQuantityOfMails;
import com.example.hairsalon.email.dto.OutOfBusinessMailNotificationDto;
import com.example.hairsalon.exception.MailSenderException;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OutOfBusinessMailServiceImpl implements OutOfBusinessMailService {

    private final JavaMailSender mailSender;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final AtomicInteger sentNotificationCounter = new AtomicInteger(0);

    private static final String FROM_MAIL = "no-reply@my-barber.com";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @Override
    @MonitorQuantityOfMails
    public int sendMailNotificationAboutOutOfBusiness(OutOfBusinessMailNotificationDto dto) {
        var setTo = getSalonUsersEmails(dto);

        int partSize = 2;
        var runnableList =
                getCallablesFromMultithreadingSending(dto, setTo, partSize, false);

        invokeAllCallables(runnableList);

        return sentNotificationCounter.get();
    }

    @Override
    @MonitorQuantityOfMails
    public int sendMailNotificationAboutUpdatedOutOfBusiness(OutOfBusinessMailNotificationDto dto) {
        String[] setTo = getSalonUsersEmails(dto);
        int partSize = 2;

        var callableList =
                getCallablesFromMultithreadingSending(dto, setTo, partSize, true);

        invokeAllCallables(callableList);

        return sentNotificationCounter.get();
    }

    private List<Callable<AtomicInteger>> getCallablesFromMultithreadingSending(
            OutOfBusinessMailNotificationDto dto,
            String[] setTo,
            int partSize,
            boolean isUpdated
    ) {
        var setToParts = Lists.partition(Arrays.stream(setTo).toList(), partSize);

        List<Callable<AtomicInteger>> callableList = new ArrayList<>();

        setToParts.forEach(
                part -> callableList.add(() -> {
                    try {
                        var message = getMimeMessage(dto, isUpdated, part.toArray(String[]::new));
                        mailSender.send(message);

                        return new AtomicInteger(sentNotificationCounter.addAndGet(part.size()));
                    } catch (MessagingException e) {
                        throw new MailSenderException(getCannotSendNotificationErrorMessage(dto.getBarbershopName()));
                    }
                })
        );
        return callableList;
    }

    private void invokeAllCallables(List<Callable<AtomicInteger>> callableList) {
        try {
            executorService.invokeAll(callableList);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private MimeMessage getMimeMessage(OutOfBusinessMailNotificationDto dto, boolean isUpdated, String[] setTo)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(
                message,
                StandardCharsets.UTF_8.toString()
        );

        String duration = getDurationOfOutOfBusiness(dto);
        String cause = getCauseOfOutOfBusiness(dto);

        messageHelper.setSubject(getSubjectForOutOfBusinessNotification(dto));
        messageHelper.setText(setTextToEmail(isUpdated, duration, cause), true);
        messageHelper.setFrom(FROM_MAIL);
        messageHelper.setTo(setTo);

        return message;
    }

    private String getDurationOfOutOfBusiness(OutOfBusinessMailNotificationDto dto) {
        String finishDateTime = dto.getFinish().format(DATE_TIME_FORMATTER);
        return String.format(
                "Out of business from %S to %S",
                dto.getStart().format(DATE_TIME_FORMATTER),
                nonNull(dto.getFinish()) ? finishDateTime : "undefined"
        );
    }

    private String getCauseOfOutOfBusiness(OutOfBusinessMailNotificationDto dto) {
        return String.format("Cause: %S", dto.getCause());
    }

    private String[] getSalonUsersEmails(OutOfBusinessMailNotificationDto dto) {
        return dto.getEmails().toArray(String[]::new);
    }

    private String getSubjectForOutOfBusinessNotification(OutOfBusinessMailNotificationDto dto) {
        return String.format("Barbershop %s is out of business", dto.getBarbershopName());
    }

    private String setTextToEmail(boolean isUpdated, String duration, String cause) {
        return String.format(
                "%S </br> %S </br> %S </br %S>",
                isUpdated ? "Updated information!" : "",
                duration,
                cause,
                isUpdated ? "" : "All of your future orders will be deleted"
        );
    }

    private String getCannotSendNotificationErrorMessage(String barbershopName) {
        return String.format(
                "Cannot send notification about out of business for barbershop with name: %s",
                barbershopName
        );
    }

    @PreDestroy
    private void shutdown() {
        executorService.shutdown();
    }

}
