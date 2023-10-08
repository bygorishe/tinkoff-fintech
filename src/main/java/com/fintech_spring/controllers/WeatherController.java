package com.fintech_spring.controllers;

import com.fintech_spring.models.Region;
import com.fintech_spring.models.Weather;
import com.fintech_spring.services.FintechWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "WeatherController", description = "Weather controller")
@RestController
@RequestMapping("api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final FintechWeatherService weatherService;

    @Operation(summary = "Get all available weather records")
    @GetMapping("/get-all-weather")
    public ResponseEntity<List<Weather>> getAllWeathers() {
        return ResponseEntity.ok(weatherService.getAllWeather());
    }

    @Operation(summary = "Get all available region")
    @GetMapping("/get-all-region")
    public ResponseEntity<List<Region>> getAllRegions() {
        return ResponseEntity.ok(weatherService.getAllRegions());
    }

    @Operation(summary = "Get the temperature in a given city")
    @GetMapping(value = {"/{city}"})
    public ResponseEntity<?> getCityWeather(@RequestParam(name = "City name") @PathVariable String city,
                                            @RequestParam(name = "Date") LocalDate date) {
        var weather = weatherService.getRegionWeather(city, date);

        return ResponseEntity.ok(weather);
    }

    @Operation(summary = "Create weather")
    @PostMapping(value = "/{city}")
    public void createWeather(@RequestParam(name = "City name") @PathVariable String city,
                              @RequestBody LocalDate date,
                              @RequestBody Double temperature) {
        weatherService.createWeather(city, date, temperature);
    }

    @Operation(summary = "Update city temperature")
    @PutMapping(value = "/{city}")
    public void updateWeather(@RequestParam(name = "City name") @PathVariable String city,
                              @RequestBody LocalDate date,
                              @RequestBody Double temperature) {
        weatherService.updateWeather(city, date, temperature);
    }

    @Operation(summary = "Delete city")
    @DeleteMapping(value = "/{city}")
    public void deleteCity(@RequestParam(name = "City name") @PathVariable String city) {
        weatherService.deleteCity(city);
    }
}
