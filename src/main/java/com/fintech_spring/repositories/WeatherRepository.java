package com.fintech_spring.repositories;

import com.fintech_spring.models.Weather;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository {
    public List<Weather> getAll();
    public void add(Weather weather);
    public Weather getWeather(String regionName, LocalDate date);
    public Weather getWeatherById(UUID id);
    public void delete(UUID id);
    public void update(Weather weather);
    public boolean exists(UUID id);
}
