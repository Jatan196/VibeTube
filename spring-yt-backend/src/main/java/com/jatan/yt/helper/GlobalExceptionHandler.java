package com.jatan.yt.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jatan.yt.exceptions.VideoUploadExceptionS3;
import com.jatan.yt.payload.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VideoUploadExceptionS3.class)
    public ResponseEntity<CustomResponse> handleS3UploadException(VideoUploadExceptionS3 ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponse.builder()
                        .message(ex.getMessage()) // Fetching message from Exception
                        .success(false)
                        .build());
    }

    // @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    // public ResponseEntity<CustomResponse>
    // handleDuplicateEntryException(SQLIntegrityConstraintViolationException ex) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(CustomResponse.builder()
    // .message("Data Already Exists in the Database!")
    // .success(false)
    // .build());
    // }

    // @ExceptionHandler(Exception.class) // General handler for all exceptions
    // public ResponseEntity<CustomResponse> handleGlobalException(Exception ex) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(CustomResponse.builder()
    // .message("An unexpected error occurred: " + ex.getMessage())
    // .success(false)
    // .build());
    // }
}
