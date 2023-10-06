package com.fintech_spring.exceptions;

public class CityAlreadyExistsException extends ApplicationException {
    public CityAlreadyExistsException(String region) {
        super("Region " + region + " already exists");
    }
}
