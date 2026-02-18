package com.autowise.demo.service;


import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.region}")
    private String region;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile file) {
        try {
            String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(bucket, key, file.getInputStream(), metadata);

            // public URL
            return String.format(
                    "https://%s.s3.%s.amazonaws.com/%s",
                    bucket, region, key
            );

        } catch (Exception e) {
            e.printStackTrace(); // ✅ see real error in console
            throw new RuntimeException("Failed to upload image to S3: " + e.getMessage(), e);
        }
    }
}