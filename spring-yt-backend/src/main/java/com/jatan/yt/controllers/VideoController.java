package com.jatan.yt.controllers;

import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.apache.tomcat.util.http.parser.MediaType;
import org.hibernate.type.TrueFalseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatan.yt.entities.Video;

import com.jatan.yt.payload.CustomMessage;
import com.jatan.yt.services.VideoService;

import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // @PostMapping
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
            @RequestParam("desc") String description) {

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoId(UUID.randomUUID().toString());

        System.out.println("Received video data - Title: " + title + ", Description: " + description);
        System.out
                .println("File details - Name: " + file.getOriginalFilename() + ", Size: " + file.getSize() + " bytes");

        Video savedVideo = videoService.save(video, file);

        if (savedVideo != null) {
            return ResponseEntity.status(HttpStatus.OK).body(video);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomMessage.builder().message("Video not uploaded ").success(false).build());
        }

    }

    @GetMapping("/stream/{videoId}")
    public ResponseEntity<Resource> stream(
            @PathVariable String videoId) {
        Video video = videoService.get(videoId);

        String contentType = video.getContentType();
        String filePath = video.getFilePath(); // or this will be the url of 3rd party video's stream service provider

        if (contentType == null) {
            contentType = "application/octet-stream"; // it is default for multipart type
        }

        FileSystemResource resource = new FileSystemResource(filePath);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    // @GetMapping("/video/{videoId}")
    // public ResponseEntity<Resource> getVideo(@PathVariable String videoId) {
    //     try {
    //         Resource resource = videoService.getVideo(videoId);
    //         String contentType = Files.probeContentType(resource.getFile().toPath());

    //         // Set default content type if null
    //         if (contentType == null) {
    //             contentType = "video/mp4";
    //         }

    //         // Validate content type
    //         if (!contentType.startsWith("video/")) {
    //             throw new IllegalArgumentException("Invalid content type: " + contentType);
    //         }

    //         return ResponseEntity
    //                 .ok()
    //                 .contentType(MediaType.parseMediaType(contentType))
    //                 .body(resource);
    //     } catch (IOException e) {
    //         throw new RuntimeException("Could not determine file type.", e);
    //     }
    // }
}
