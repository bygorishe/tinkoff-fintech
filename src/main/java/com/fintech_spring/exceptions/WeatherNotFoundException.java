package com.fintech_spring.exceptions;

import java.util.UUID;

public class WeatherNotFoundException extends ApplicationException {
    public WeatherNotFoundException() {
        super("Weather not found");
    }
}
