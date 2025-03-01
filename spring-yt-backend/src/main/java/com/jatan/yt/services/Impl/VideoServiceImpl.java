package com.jatan.yt.services.Impl;

import java.io.File;
import java.io.IOException;
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

    @Value("${files.video.hls}")
    private String HLS_DIR;

    // TO check whether the DIR exists or not
    @PostConstruct // we will use Bean Life Cycle
    public void init() {
        File file = new File(DIR);
        // File file1=new File(HLS_DIR); // or can use createDirectories method from
        // Files class, it creates all parent folders(which doesn'ts exists) first, then
        // create final folder , unlike createDirectory() method
        // This method rthrows an exception, therefore needs to be handled
        try {
            Files.createDirectories(Paths.get(HLS_DIR));
        } catch (Exception e) {
            // TODO: handle exception
        }

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

            // place the actual content of video file
            Files.copy(fileData, path, StandardCopyOption.REPLACE_EXISTING);

            // now will add these processed stuff into db through video entity
            video.setContentType(contentType);
            video.setFilePath(path.toString());

            // processing video
            processVideo(video.getVideoId());

            // delete the actual file , if any error occurs in above processing of videos

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
        return videoRepository.findAll();
    }

    @Override
    public String processVideo(String videoId) {
        // path where to store data(HLS Playlist)

        Video video = this.get(videoId);
        String filePath = video.getFilePath(); // it is coming from DB that'why stored in String format

        Path videoPath = Paths.get(filePath);

        // String output360px=HLS_DIR+videoId+"/360px/";
        // String output720px=HLS_DIR+videoId+"/720px/";
        // String output1080px=HLS_DIR+videoId+"/1080px/";

        try {
            // Files.createDirectories(Paths.get(output360px));
            // Files.createDirectories(Paths.get(output720px));
            // Files.createDirectories(Paths.get(output1080px));
            // ffmpeg commmands
            Path outputPath = Paths.get(HLS_DIR, videoId);

            System.out.println("Started processing video");
            Files.createDirectories(outputPath);
            // String ffmpegCmd = "ffmpeg -i " + inputFile
            // + " -codec: copy -start_number 0 -hls_time 10 -hls_list_size 0 -f hls
            // output.m3u8";

            String ffmpegCmd = String.format(
                    "ffmpeg -i \"%s\" -c:v libx264 -c:a aac -strict -2 -f hls -hls_time 10 -hls_list_size 0 -hls_segment_filename \"%s/segment_%%3d.ts\"  \"%s/master.m3u8\" ",
                    videoPath, outputPath, outputPath);

            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", ffmpegCmd);
            processBuilder.inheritIO();

            Process process = processBuilder.start();

            int exit = process.waitFor(); // this method also returns an exception

            if (exit != 0) {
                throw new RuntimeException("video processing failed!!");
            }

            System.out.println("Completely processed");
            return videoId;
        } catch (IOException IO) {
            // TODO: handle exception
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
