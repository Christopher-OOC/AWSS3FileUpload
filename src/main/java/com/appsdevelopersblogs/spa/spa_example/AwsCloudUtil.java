package com.appsdevelopersblogs.spa.spa_example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.*;

public class AwsCloudUtil {

    private AWSCredentials awsCredentials(String accessKey, String secretKey) {
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        return credentials;
    }

    private AmazonS3 awsS3ClientBuilder(String accessKey, String secretKey) {

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(accessKey, secretKey)))
                .withRegion(Regions.US_EAST_2)
                .build();

        return s3Client;
    }

    public void uploadFileToS3(String filename, byte[] fileBytes, String accessKey,
                               String secretKey, String bucket) throws FileNotFoundException {

        AmazonS3 s3Client = awsS3ClientBuilder(accessKey, secretKey);

        File file = new File(filename);


        try (OutputStream os = new FileOutputStream(file)) {
            os.write(fileBytes);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        s3Client.putObject(bucket, filename, file);
    }

    public S3ObjectInputStream downloadFileFroomS3(String filename,
                                                   String accessKey,
                                                   String secretKey,
                                                   String bucket) {
        AmazonS3 s3Client = awsS3ClientBuilder(accessKey, secretKey);
        S3Object s3Object = s3Client.getObject(bucket, filename);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        return inputStream;
    }




}
