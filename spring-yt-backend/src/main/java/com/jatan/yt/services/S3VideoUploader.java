package com.jatan.yt.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3VideoUploader {
    String uploadVideo(MultipartFile video);

    List<String> allFiles();

    String preSignedUrl();// provide timed access to videos on s3
}
