package com.jatan.yt.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;

import org.springframework.http.MediaType;
import org.apache.catalina.connector.Response;
import org.hibernate.type.TrueFalseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatan.yt.entities.Video;

import com.jatan.yt.payload.CustomMessage;
import com.jatan.yt.services.VideoService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public ResponseEntity<?> create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
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

    // url will be -->
    // http://localhost:8080/api/v1/videos/stream/b571ef27-6121-4924-9668-61e0fa31d441
    @GetMapping("/stream/{videoId}")
    public ResponseEntity<Resource> stream(
            @PathVariable String videoId) {
        Video video = videoService.get(videoId);

        String contentType = video.getContentType();
        String filePath = video.getFilePath(); // or this will be the url of 3rd party video's stream service provider

        if (contentType == null) {
            contentType = "application/octet-stream"; // it is default for multipart type
        }

        Resource resource = new FileSystemResource(filePath);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/stream/range/{videoId}")
    public ResponseEntity<Resource> streamVideoRange(
            @PathVariable String videoId,
            @RequestHeader(value = "Range", required = false) String range) {
        System.out.println("range");

        Video video = videoService.get(videoId);
        Path path = Paths.get(video.getFilePath());

        Resource resource = new FileSystemResource(path);

        String contentType = video.getContentType();

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // file ki length
        long fileLength = path.toFile().length();

        if (range == null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource); // will return the whole video
        }

        // Extracting and standardising the start and end pointers for video range
        long rangeS, rangeE;

        String[] ranges = range.replace("bytes ", "").split("-");

        rangeS = Long.parseLong(ranges[0]);

        if (ranges.length > 1) {
            rangeE = Long.parseLong(ranges[1]);
        } else {
            rangeE = fileLength - 1;
        }

        if (rangeE > fileLength - 1)
            rangeE = fileLength - 1;

        InputStream inputStream;

        try {
            inputStream = Files.newInputStream(path); // its like pointer

            inputStream.skip(rangeS);

        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        long contentLength=rangeE-rangeS+1;

        // Now we will add some headers for security purpose
        HttpHeaders headers=new HttpHeaders(null);

        headers.add("Content-Range","bytes "+rangeS+"-"+rangeE+)


        return null;
    }

    @GetMapping("/getAll")
    public List<Video> getAll() {
        return videoService.getAll();
    }
}
