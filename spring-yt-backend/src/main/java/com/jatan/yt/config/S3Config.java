package com.jatan.yt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;



@Configuration
public class S3Config {

    @Value("cloud.aws.credentials.access-key")
    private String awsAccessKey;

    @Value("cloud.aws.credentials.secret-key")
    private String awsSecretKey;

    @Value("jatan.aws.region.static")
    private String region;

    @Bean
    public AmazonS3 client() {
        // From client method of configruation file , will return an object of amazon
        // s3, with above credentails
        // point to be known is that, what extra features and methdos does this new
        // object will be having
        // which forces us to use their sdk's provided object , and not our POJO with
        // above credentials as their attributes

        AWSCredentials crd = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(crd))
                .withRegion(region)
                .build();

        return amazonS3;
    }
}