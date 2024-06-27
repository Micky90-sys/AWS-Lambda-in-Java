package com.example.awslambda.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class S3Service {

    private AmazonS3 s3Client;
    private String bucketName = "your-bucket-name";

    public S3Service() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("your-access-key", "your-secret-key");
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public String uploadFile(String fileName, String content) {
        try {
            File file = new File("/tmp/" + fileName);
            try (OutputStream os = new FileOutputStream(file)) {
                os.write(content.getBytes());
            }
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
            return "File uploaded successfully";
        } catch (IOException e) {
            return "Error uploading file: " + e.getMessage();
        }
    }

    public String downloadFile(String fileName) {
        try {
            S3Object s3Object = s3Client.getObject(bucketName, fileName);
            return "File downloaded: " + s3Object.getObjectContent().getHttpRequest().getURI().toString();
        } catch (Exception e) {
            return "Error downloading file: " + e.getMessage();
        }
    }
}
