package com.example.hairsalon.s3storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.exception.ResourceWriteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObjectStorageServiceImpl implements ObjectStorageService {

    private final AmazonS3 s3Client;

    @Override
    public boolean isBucketExisting(String bucketName) {
        return s3Client.doesBucketExistV2(bucketName);
    }

    @Override
    public void makeBucket(String bucketName) {
        checkBucketExistence(bucketName);
        s3Client.createBucket(bucketName);
    }

    @Override
    public List<String> getAllBucketsList() {
        return s3Client.listBuckets().stream()
                .map(Bucket::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void removeBucket(String bucketName) {
        checkBucketEmptiness(bucketName);
        s3Client.deleteBucket(bucketName);
    }

    @Override
    public List<String> getAllFilesNamesInBucket(String bucketName) {
        return s3Client.listObjects(bucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void uploadFile(MultipartFile file, String bucketName, String fileName) {
        try {
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getInputStream().available());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    fileName,
                    file.getInputStream(),
                    objectMetadata
            );

            s3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new ResourceWriteException("Cannot upload file");
        }
    }

    @Override
    public S3Object downloadFile(String bucketName, String objectName) {
        checkObjectExistence(bucketName, objectName);
        return s3Client.getObject(bucketName, objectName);
    }

    @Override
    public void removeObject(String bucketName, String objectName) {
        checkObjectExistence(bucketName, objectName);
        s3Client.deleteObject(bucketName, objectName);
    }

    private void checkObjectExistence(String bucketName, String objectName) {
        if (!s3Client.doesObjectExist(bucketName, objectName)) {
            throw new ResourceNotFoundException(
                    String.format("Cannot find object with name: %s, in bucket: %s", objectName, bucketName)
            );
        }
    }

    private void checkBucketExistence(String bucketName) {
        if (isBucketExisting(bucketName)) {
            throw new ResourceWriteException(
                    String.format("Bucket with name: %s already exists", bucketName)
            );
        }
    }

    private void checkBucketEmptiness(String bucketName) {
        if (!getAllFilesNamesInBucket(bucketName).isEmpty()) {
            throw new ResourceWriteException(
                    String.format("Cannot delete not empty bucket: %S", bucketName)
            );
        }
    }

}
