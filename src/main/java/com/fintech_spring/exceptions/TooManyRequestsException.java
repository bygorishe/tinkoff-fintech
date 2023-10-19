package com.fintech_spring.exceptions;

public class TooManyRequestsException extends ApplicationException {
    public TooManyRequestsException() {
        super("Too many requests");
    }
}
