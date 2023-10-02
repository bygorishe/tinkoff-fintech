package com.example.fintech_spring.controllers;

import com.example.fintech_spring.models.Weather;
import com.example.fintech_spring.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "WeatherController", description = "Weather API controller")
@RestController
@RequestMapping("api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @Operation(summary = "Get all available weather records")
    @GetMapping("get-all")
    public ResponseEntity<List<Weather>> getAllWeather() {
        var weather = weatherService.getAllWeather();

        return ResponseEntity.ok(weather);
    }

    @Operation(summary = "Get the temperature in a given city")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Temperature in the city for the current date"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No information found"
            )
    })
    @GetMapping(value = {"{city}"})
    public ResponseEntity<?> getCityWeather(@Parameter(description = "City name") @PathVariable String city) {
        var weather = weatherService.getRegionWeather(city);

        if (weather == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(weather);
    }

    @Operation(summary = "Create new city")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "City was created"
            )
    })
    @PostMapping(value = "/{city}")
    public ResponseEntity<?> createWeather(@Parameter(description = "City name") @PathVariable String city) {
        var isCreated = weatherService.createWeather(city);
        if (isCreated)
            return ResponseEntity.ok("created");
        return ResponseEntity.badRequest().body("not create");
    }

    @Operation(summary = "Update city temperature")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "City weather was updated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found"
            )
    })
    @PutMapping(value = "/{city}")
    public ResponseEntity<?> updateWeather(@Parameter(description = "City name") @PathVariable String city) {
        var isUpdated = weatherService.updateWeather(city);
        if (isUpdated)
            return ResponseEntity.ok("updated");
        return ResponseEntity.badRequest().body("not update");
    }

    @Operation(summary = "Delete city")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "City weather was deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found"
            )
    })
    @DeleteMapping(value = "/{city}")
    public ResponseEntity<?> deleteWeather(@Parameter(description = "City name") @PathVariable String city) {
        var isDeleted = weatherService.deleteWeather(city);
        if (isDeleted)
            return ResponseEntity.ok("deleted");
        return ResponseEntity.badRequest().body("not delete");
    }

    @Operation(summary = "Get cities with temperatures higher than a given one")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "City with higher temperature"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Information not found"
            )
    })
    @GetMapping(value = {"city-with-higher-temp/{temperature}"})
    public ResponseEntity<?> getRegionsWithHigherTemperature(@Parameter(description = "Temperature")
                                                                 @PathVariable Integer temperature) {
        var weather = weatherService.getRegionsWithHigherTemperature(temperature);

        if (weather == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(weather);
    }

    @Operation(summary = "Get weather history in the city")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = ""
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Information not found"
            )
    })
    @GetMapping(value = {"get-Regions-Temperatures-Map"})
    public ResponseEntity<?> getRegionsTemperaturesMap() {
        var weather = weatherService.getRegionsTemperaturesMap();

        return ResponseEntity.ok(weather);
    }

    @Operation(summary = "Get weather history group by temperature")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = ""
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Information not found"
            )
    })
    @GetMapping(value = {"get-Temperature-Weather-Map"})
    public ResponseEntity<?> getTemperatureWeatherMap() {
        var weather = weatherService.getTemperatureWeatherMap();

        return ResponseEntity.ok(weather);
    }
}
