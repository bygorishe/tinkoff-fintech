package com.fintech_spring.exceptions;

import org.springframework.http.HttpStatus;

public class NoRemoteServiceResponseException extends RemoteWeatherException {
    public NoRemoteServiceResponseException() {
        super("Service is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }
}