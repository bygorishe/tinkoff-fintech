package com.fintech_spring.exceptions;

public class CityNotFoundException extends ApplicationException {
    public CityNotFoundException(String region) {
        super("Region " + region + " not found");
    }
}
