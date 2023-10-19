package fintech.services;

import fintech.models.Weather;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class WeatherService {
    private static final int TEMPERATURE_SPREAD = 50;

    public static List<Weather>  generateWeatherList(Map<UUID, String> regions) {
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

    public static Map<String, Double> calculateAverageTemperature(List<Weather> weathers){
        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getRegionName,
                        Collectors.averagingDouble(Weather::getTemperature)));
    }

    public static List<String> getRegionsWithHigherTemperature(List<Weather> weathers, Integer temperature) {
        var average = calculateAverageTemperature(weathers);
        return average.entrySet()
                .stream()
                .filter(x -> x.getValue() > temperature)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static Map<UUID, List<Integer>> getRegionsTemperaturesMap(List<Weather> weathers) {
        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getRegionId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public static Map<Integer, List<Weather>> getTemperatureWeatherMap(List<Weather> weathers) {
        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature));
    }
}
