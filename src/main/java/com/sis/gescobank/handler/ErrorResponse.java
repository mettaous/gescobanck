package com.sis.gescobank.handler;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String message;
    private final int code;
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

