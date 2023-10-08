package com.fintech_spring.services;

import com.fintech_spring.exceptions.CityAlreadyExistsException;
import com.fintech_spring.exceptions.CityNotFoundException;
import com.fintech_spring.exceptions.WeatherNotFoundException;
import com.fintech_spring.models.Region;
import com.fintech_spring.models.Weather;
import com.fintech_spring.repositories.RegionRepository;
import com.fintech_spring.repositories.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FintechWeatherService implements WeatherService{
    private final WeatherRepository weathersRepository;
    private final RegionRepository regionRepository;

    @Override
    public List<Weather> getAllWeather(){
        return weathersRepository.getAll();
    }

    @Override
    public List<Region> getAllRegions(){
        return regionRepository.getAll();
    }

    @Override
    public Weather getRegionWeather(String regionName, LocalDate date){
        var weathers = weathersRepository.getAll();

        if(!regionRepository.exists(regionName)){
            throw new CityNotFoundException(regionName);
        }

        Weather weather;
        try {
            weather = weathersRepository.getWeather(regionName, date);
        } catch (Exception e) {
            throw new WeatherNotFoundException();
        }

        return weather;
    }

    @Override
    public void createWeather(String regionName, LocalDate date, Double temperature) {
        if (regionRepository.exists(regionName)) {
            throw new CityAlreadyExistsException(regionName);
        }

        var newRegion = Region.builder()
                .id(UUID.randomUUID())
                .name(regionName)
                .build();
        regionRepository.add(newRegion);

        var newWeather = Weather.builder()
                .id(UUID.randomUUID())
                .regionId(newRegion.getId())
                .regionName(regionName)
                .temperature(temperature)
                .date(date)
                .build();

        weathersRepository.add(newWeather);
    }

    @Override
    public void updateWeather(String regionName, LocalDate date, Double temperature){
        if(!regionRepository.exists(regionName)) {
            throw new CityNotFoundException(regionName);
        }

        Weather weather;
        try {
            weather = weathersRepository.getWeather(regionName, date);
        } catch (Exception e) {
            throw new WeatherNotFoundException();
        }

        weather.setTemperature(temperature);

        weathersRepository.update(weather);
    }

    @Override
    public void deleteWeather(String regionName, LocalDate date){
        if(!regionRepository.exists(regionName)) {
            throw new CityNotFoundException(regionName);
        }

        Weather weather;
        try {
            weather = weathersRepository.getWeather(regionName, date);
        } catch (Exception e) {
            throw new WeatherNotFoundException();
        }

        weathersRepository.delete(weather.getId());
    }

    @Override
    public void deleteCity(String regionName) {
        if(!regionRepository.exists(regionName)) {
            throw new CityNotFoundException(regionName);
        }

        var id = regionRepository.getByName(regionName).getId();
        regionRepository.delete(id);
    }

   /* public Map<String, Double> calculateAverageTemperature(){
        var weathers = weathersRepository.getWeatherHashMap();

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
        var weathers = weathersRepository.getWeatherHashMap();

        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getRegionId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public Map<Integer, List<Weather>> getTemperatureWeatherMap() {
        var weathers = weathersRepository.getWeatherHashMap();

        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature));
    }*/
}
