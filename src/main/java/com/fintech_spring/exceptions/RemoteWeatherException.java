package com.fintech_spring.exceptions;

import org.springframework.http.HttpStatus;

public class RemoteWeatherException extends ApplicationException {
    private final HttpStatus status;

    protected RemoteWeatherException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
