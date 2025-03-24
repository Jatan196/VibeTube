package com.jatan.yt.services.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.jatan.yt.exceptions.VideoUploadExceptionS3;

import com.jatan.yt.services.S3VideoUploader;

public class S3VideoUploaderImpl implements S3VideoUploader {

    @Autowired
    private AmazonS3 client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadVideo(MultipartFile video) {
        // TODO Auto-generated method stub
        try {
            String fileName = video.getOriginalFilename();

            String modifiedName = fileName + UUID.randomUUID().toString();
            InputStream fileData = video.getInputStream(); // for reading the content of file

            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(video.getSize());

            // Date expD=new Date(2025,3,30);
            // metaData.setExpirationTime()

            PutObjectResult res = client.putObject(
                    new PutObjectRequest(bucketName, modifiedName, fileData, metaData));
            System.out.println(res);
            // String contentType = video.getContentType();

        } catch (IOException e) {
            throw new VideoUploadExceptionS3(bucketName);
        }
        return null;
    }

    @Override
    public List<String> allFiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'allFiles'");
    }

    @Override
    public String preSignedUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'preSignedUrl'");
    }

}
