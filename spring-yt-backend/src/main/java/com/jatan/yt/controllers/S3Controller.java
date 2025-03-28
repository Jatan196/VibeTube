package com.jatan.yt.controllers;

import org.hibernate.event.spi.ResolveNaturalIdEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatan.yt.services.S3VideoUploader;

@RestController
@RequestMapping("api/v1/s3")

public class S3Controller {

    @Autowired
    private S3VideoUploader uploader;

    @PostMapping("upload")
    public ResponseEntity<?> uploadVideo(
            @RequestParam("file") MultipartFile f) {
        if (f == null) {
            return ResponseEntity.status(402).body("Video is not uploaded");

        }
        System.out.println(uploader);
        return ResponseEntity.ok(uploader.uploadVideo(f));
    }

}
