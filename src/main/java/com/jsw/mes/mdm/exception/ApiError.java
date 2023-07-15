package com.jsw.mes.mdm.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ApiError {
    private String message;
    private HttpStatus status;

    private Instant now;

    public Instant getNow() {
        return now;
    }

    public void setNow(Instant now) {
        this.now = now;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public ApiError(String message, HttpStatus status, Instant now) {
        this.message = message;
        this.status = status;
        this.now = now;
    }

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
