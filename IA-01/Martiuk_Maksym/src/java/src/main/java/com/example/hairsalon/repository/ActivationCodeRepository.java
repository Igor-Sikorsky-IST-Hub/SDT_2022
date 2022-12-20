package com.example.hairsalon.repository;

public interface ActivationCodeRepository {

    Integer findByActivationCode(String code);

}
