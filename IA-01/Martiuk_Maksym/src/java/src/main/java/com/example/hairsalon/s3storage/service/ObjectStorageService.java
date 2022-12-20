package com.example.hairsalon.s3storage.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjectStorageService {

    boolean isBucketExisting(String bucketName);

    void makeBucket(String bucketName);

    List<String> getAllBucketsList();

    void removeBucket(String bucketName);

    List<String> getAllFilesNamesInBucket(String bucketName);

    void uploadFile(MultipartFile file, String bucketName, String filename);

    S3Object downloadFile(String bucketName, String objectName);

    void removeObject(String bucketName, String objectName);

}
