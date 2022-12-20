package com.example.hairsalon.exception;

public class MailSenderException extends RuntimeException {
    public MailSenderException(String message) {
        super(message);
    }
}
