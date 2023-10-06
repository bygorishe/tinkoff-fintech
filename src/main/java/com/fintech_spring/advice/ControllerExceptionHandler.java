package com.fintech_spring.advice;

import com.fintech_spring.exceptions.CityAlreadyExistsException;
import com.fintech_spring.exceptions.CityNotFoundException;
import com.fintech_spring.exceptions.WeatherNotFoundException;
import com.fintech_spring.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCityNotFoundException(CityNotFoundException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(WeatherNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCityNotFoundException(WeatherNotFoundException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(CityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCityAlreadyExistsException(CityAlreadyExistsException exception) {
        return handleException(exception);
    }

    private ErrorResponse handleException(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
