package com.example.fintech_spring.services;



import com.example.fintech_spring.models.Weather;
import com.example.fintech_spring.repositories.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final WeatherRepository weathersRepository;

    public WeatherService(WeatherRepository weathersRepository) {
        this.weathersRepository = weathersRepository;
    }

    public List<Weather> getAllWeather(){
        return weathersRepository.getWeatherList();
    }

    public Weather getRegionWeather(String region){
        var weathers = weathersRepository.getWeatherList();

        return weathers.stream()
                .filter(x -> x.getRegionName().equals(region))
                .filter(x -> x.getDate().toLocalDate().isEqual(LocalDate.now()))
                .findFirst().orElseThrow();
    }

    public boolean createWeather(String region){
        weathersRepository.addNewRegion(region);
        var regionId = weathersRepository.getRegionId(region);
        var random = new Random();

        var newWeather = Weather.builder()
                        .regionId(regionId)
                        .regionName(region)
                        .temperature(random.nextInt(-50, 50))
                        .date(LocalDateTime.now())
                        .build();

        return weathersRepository.addWeather(newWeather);
    }

    public boolean updateWeather(String region){
        if(!weathersRepository.isRegionExist(region))
            return false;

        var weathers = weathersRepository.getWeatherList();
        var weather = weathers.stream()
                .filter(x -> x.getRegionName().equals(region))
                .filter(x -> x.getDate().toLocalDate().isEqual(LocalDate.now()))
                .findFirst().orElseThrow();
        var random = new Random();

        weather.setTemperature(random.nextInt(-50, 50));

        return true;
    }

    public boolean deleteWeather(String region){
        if(!weathersRepository.isRegionExist(region))
            return false;

        var weathers = weathersRepository.getWeatherList();
        weathers.removeIf(w -> Objects.equals(w.getRegionName(), region));

        return true;
    }

    public Map<String, Double> calculateAverageTemperature(){
        var weathers = weathersRepository.getWeatherList();

        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getRegionName,
                        Collectors.averagingDouble(Weather::getTemperature)));
    }

    public List<String> getRegionsWithHigherTemperature(Integer temperature) {
        var average = calculateAverageTemperature();

        return average.entrySet()
                .stream()
                .filter(x -> x.getValue() > temperature)
                .map(Map.Entry::getKey)
                .toList();
    }

    public Map<UUID, List<Integer>> getRegionsTemperaturesMap() {
        var weathers = weathersRepository.getWeatherList();

        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getRegionId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public Map<Integer, List<Weather>> getTemperatureWeatherMap() {
        var weathers = weathersRepository.getWeatherList();

        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature));
    }
}
