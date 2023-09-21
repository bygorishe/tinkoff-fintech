package fintech.example.logic;

import fintech.example.models.Weather;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {
    private final int temperatureSpread = 50;
    private final HashMap<UUID, String> regions = new HashMap<>();
    private final ArrayList<Weather> weathers = new ArrayList<>();

    public Logic(){
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

        generateWeatherList();
    }

    private void generateWeatherList() {
        Random random = new Random();

        for(int i = 0; i < 3; i++) {
            for (var item : regions.entrySet()) {
                weathers.add(Weather.builder()
                        .regionId(item.getKey())
                        .regionName(item.getValue())
                        .temperature(random.nextInt(-temperatureSpread, temperatureSpread))
                        .datetime(LocalDateTime.now().minusDays(i))
                        .build());
            }
        }
    }

    public ArrayList<Weather> getWeathersList(){
        return weathers;
    }

    public Map<String, Double> calculateAverageTemperature(){
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
        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getRegionId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public Map<Integer, List<Weather>> getTemperatureWeatherMap() {
        return weathers.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature));
    }
}
