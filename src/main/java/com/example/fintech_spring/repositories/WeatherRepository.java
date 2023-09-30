package com.example.fintech_spring.repositories;

import com.example.fintech_spring.models.Weather;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class WeatherRepository {
    private static final int TEMPERATURE_SPREAD = 50;
    private final HashMap<UUID, String> regions = new HashMap<>();
    private final List<Weather> weatherList;

    public WeatherRepository(){
        generateRegions();
        weatherList = generateWeatherData();
    }

    public List<Weather> getWeatherList(){
        return weatherList;
    }

    public boolean addNewRegion(String region){
        if(isRegionExist(region))
            return false;

        regions.put(UUID.randomUUID(), region);

        return true;
    }

    public boolean isRegionExist(String region) {
        return regions.containsValue(region);
    }

    public UUID getRegionId(String region){
        return regions.entrySet()
                .stream()
                .filter(x -> x.getValue().equals(region))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
    }

    private void generateRegions() {
        regions.put(UUID.randomUUID(), "Novosibirsk");
        regions.put(UUID.randomUUID(), "Saint-Petersburg");
        regions.put(UUID.randomUUID(), "Moscow");
        regions.put(UUID.randomUUID(), "Kemerovo");
        regions.put(UUID.randomUUID(), "Berdsk");
        regions.put(UUID.randomUUID(), "Pashino");
        regions.put(UUID.randomUUID(), "Iskitim");
        regions.put(UUID.randomUUID(), "Barabinsk");
        regions.put(UUID.randomUUID(), "Karasuk");
        regions.put(UUID.randomUUID(), "Irkutsk");
    }

    public boolean addWeather(Weather weather){
        weatherList.add(weather);

        return true;
    }

    private List<Weather> generateWeatherData(){
        Random random = new Random();
        var weathers = new ArrayList<Weather>();

        for(int i = 0; i < 3; i++) {
            for (var item : regions.entrySet()) {
                weathers.add(Weather.builder()
                        .regionId(item.getKey())
                        .regionName(item.getValue())
                        .temperature(random.nextInt(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .date(LocalDateTime.now().minusDays(i))
                        .build());
            }
        }

        return weathers;
    }
}
