package com.jatan.yt.services;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface S3VideoUploader {
    String uploadVideo(MultipartFile video);

    List<String> allFiles();

    String preSignedUrl();// provide timed access to videos on s3
}
