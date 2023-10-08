package com.fintech_spring.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech_spring.exceptions.ApplicationException;
import com.fintech_spring.exceptions.NoRemoteServiceResponseException;
import com.fintech_spring.models.Region;
import com.fintech_spring.models.Weather;
import com.fintech_spring.repositories.RegionRepository;
import com.fintech_spring.repositories.WeatherRepository;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeatherApiService {
    @Value("${weatherapi.api-key}")
    private String SECRET_KEY;
    private final WebClient weatherWebClient;
    private final RateLimiter rateLimiter;
    private final RegionRepository regionRepository;
    private final WeatherRepository weatherRepository;

    public Weather getWeather(String regionName) {

        var response = weatherWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", SECRET_KEY)
                        .queryParam("q", regionName)
                        .queryParam("aqi", "yes")
                        .build()
                ).retrieve()
                .onStatus(
                        httpStatusCode -> httpStatusCode.is4xxClientError() ||
                                httpStatusCode.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(ApplicationException.class)
                                .flatMap(errorResponseWeatherAPI -> Mono.error(
                                                new ApplicationException(errorResponseWeatherAPI.getMessage())
                                        )
                                )
                )
                .toEntity(String.class)
                .transformDeferred(RateLimiterOperator.of(rateLimiter))
                .block();

        if(response == null)
            throw new NoRemoteServiceResponseException();

        var jsonBody = (String) response.getBody();
        var mapper = new ObjectMapper();
        Weather weather;
        UUID regionId;

        if(regionRepository.exists(regionName)){
            regionId = regionRepository.getByName(regionName).getId();
        }
        else {
            regionId = UUID.randomUUID();
            var region = Region.builder()
                    .id(regionId)
                    .name(regionName)
                    .build();
            regionRepository.add(region);
        }

        try {
            JsonNode jsonNode = mapper.readTree(jsonBody);
            weather = Weather.builder()
                    .id(UUID.randomUUID())
                    .regionName(regionName)
                    .regionId(regionId) //Todo get id from repo
                    .date(LocalDate.now()) //Todo parse
                    .temperature(Double.valueOf(String.valueOf(jsonNode.get("current").get("temp_c"))))
                    .build();
        } catch (JsonProcessingException e) {
            throw new ApplicationException(e.getMessage());
        }
        weatherRepository.add(weather);

        return weather;
    }
}
