package com.jatan.yt.services.Impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import com.jatan.yt.entities.Video;
import com.jatan.yt.repositories.VideoRepository;
import com.jatan.yt.services.VideoService;

import jakarta.annotation.PostConstruct;

@Service
public class VideoServiceImpl implements VideoService {

    @Value("${files.video}")
    private String DIR;

    // TO check whether the DIR exists or not
    @PostConstruct // we will use Bean Life Cycle
    public void init() {
        File file = new File(DIR);

        if (!file.exists()) {
            file.mkdir();
            System.out.println("New folder is created");
        } else
            System.out.println("Already exisits");
    }

    @Autowired
    public VideoRepository videoRepository; // or can also make constructor

    @Override
    public Video save(Video video, MultipartFile file) {

        System.out.println("fadfa");
        Video temp = new Video();

        try {
            // Create folder & file path
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream fileData = file.getInputStream(); // for reading the content of file

            // cleaning "//" of ..
            String cleanFileName = StringUtils.cleanPath(fileName);
            String cleanFolder = StringUtils.cleanPath(DIR);

            System.out.println("cleanFileName: " + cleanFileName);
            System.out.println("cleanFolder: " + cleanFolder);

            // folder path with filename
            Path path = Paths.get(cleanFolder, cleanFileName);
            System.out.println("path: " + path);
            System.out.println(contentType);

            Files.copy(fileData, path, StandardCopyOption.REPLACE_EXISTING);

            // now will add these processed stuff into db through video entity
            video.setContentType(contentType);
            video.setFilePath(path.toString());

            // meta data of saved in DB , through repository

            return videoRepository.save(video);

        } catch (Exception e) {
            e.printStackTrace();
            // return null;
        }

        return null;
    }

    @Override
    public Video get(String videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException());
        return video;
    }

    @Override
    public Video getByTitle(String title) {
        return null;
    }

    @Override
    public List<Video> getAll() {
        return List.of();
    }

}
