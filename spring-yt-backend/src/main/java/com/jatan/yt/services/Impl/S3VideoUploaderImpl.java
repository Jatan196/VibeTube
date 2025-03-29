package com.jatan.yt.services.Impl; // Changed package name to follow standard lowercase convention

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jatan.yt.exceptions.VideoUploadExceptionS3;
import com.jatan.yt.services.S3VideoUploader;

@Service // Use @Service instead of @Component
public class S3VideoUploaderImpl implements S3VideoUploader {

    @Autowired
    private AmazonS3 client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadVideo(MultipartFile video) {

        try {
            if (video == null || video.isEmpty()) {
                throw new VideoUploadExceptionS3("Empty video file cannot be uploaded.");
            }

            String originalFileName = video.getOriginalFilename();
            String fileExtension = originalFileName != null && originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : "";

            // Generate unique file name
            String modifiedName = UUID.randomUUID().toString() + fileExtension;
            InputStream fileData = video.getInputStream();

            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(video.getSize());
            metaData.setContentType(video.getContentType());

            System.out.println("Uploading to S3: " + modifiedName);
            System.out.println("Uploading to S3: " + modifiedName);
            client.putObject(new PutObjectRequest(bucketName, modifiedName, fileData, metaData));

            System.out.println("Uploaded complete: " + modifiedName);

            return this.preSignedUrl(originalFileName);// modifiedName;// client.getUrl(bucketName,
                                                       // modifiedName).toString();

        } catch (IOException e) {
            throw new VideoUploadExceptionS3("Failed to upload video to S3: " + e.getMessage());
        }
    }
  
    @Override
    public List<String> allFiles() {
        throw new UnsupportedOperationException("Method 'allFiles' is not implemented yet.");
    }

    @Override
    public String preSignedUrl(String fileName) {

        Date expirationDate = new Date(0);

        long time = expirationDate.getTime();
        int duration = 2; // in hours

        time = time + duration * 60 * 60 * 1000;
        expirationDate.setTime(time);

        GeneratePresignedUrlRequest reqObj = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationDate);

        URL url = client.generatePresignedUrl(reqObj);

        return url.toString();
    }
}
