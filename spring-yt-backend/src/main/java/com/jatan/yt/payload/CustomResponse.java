package com.jatan.yt.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomResponse {
    private String message;
    private boolean success;
    private Object data; // Optional: Used for sending additional data

    public static CustomResponse success(String message, Object data) {
        return CustomResponse.builder()
                .message(message)
                .success(true)
                .data(data)
                .build();
    }

    public static CustomResponse failure(String message) {
        return CustomResponse.builder()
                .message(message)
                .success(false)
                .build();
    }
}
