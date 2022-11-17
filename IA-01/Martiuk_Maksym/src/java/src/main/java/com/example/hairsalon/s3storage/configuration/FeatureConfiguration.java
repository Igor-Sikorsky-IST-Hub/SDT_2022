package com.example.hairsalon.s3storage.configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.example.hairsalon.s3storage.service.ObjectStorageService;
import com.example.hairsalon.s3storage.service.ObjectStorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureConfiguration {

    @Bean
    public ObjectStorageService objectStorageService(AmazonS3 s3Client) {
        return ObjectStorageServiceImpl.getInstance(s3Client);
    }
}
