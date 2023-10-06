package com.fintech_spring.services;

import com.fintech_spring.models.Region;
import com.fintech_spring.models.Weather;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {
    public List<Weather> getAllWeather();
    public List<Region> getAllRegions();
    public Weather getRegionWeather(String region, LocalDate date);
    public void createWeather(String region, LocalDate date, Integer temperature);
    public void updateWeather(String region, LocalDate date, Integer temperature);
    public void deleteWeather(String region, LocalDate date);
    public void deleteCity(String region);
}
