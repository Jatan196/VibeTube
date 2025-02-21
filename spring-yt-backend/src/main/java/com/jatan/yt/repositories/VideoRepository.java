package com.jatan.yt.repositories;

import com.jatan.yt.entities.Video;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Optional<Video> findByTitle(String title);

    // query methods

    // native

    // criteria api

    // Additional query methods if needed
}

