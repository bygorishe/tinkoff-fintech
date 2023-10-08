package com.fintech_spring.controllers;

import com.fintech_spring.services.WeatherApiService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "WeatherApiController", description = "WeatherApi service controller")
@RestController
@RequestMapping("api/v1/weather-api")
@RequiredArgsConstructor
public class WeatherApiController {
    private final WeatherApiService weatherApiService;

    @Operation(summary = "Get weather from WeatherAPI service")
    @GetMapping("/{city}")
    @RateLimiter(name = "weatherapi-ratelimiter")
    public ResponseEntity<?> doGet(@RequestParam(name = "City name") @PathVariable String city) {
        var weather = weatherApiService.getWeather(city);

        return ResponseEntity.ok(weather);
    }
}
