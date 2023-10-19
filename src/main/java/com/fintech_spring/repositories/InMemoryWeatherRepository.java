package com.fintech_spring.repositories;

import com.fintech_spring.models.Weather;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryWeatherRepository implements WeatherRepository {
    private final Map<UUID, Weather> weatherMap;

    public InMemoryWeatherRepository(){
        weatherMap = generateWeather();
    }

    @Override
    public List<Weather> getAll(){
        return weatherMap.values().stream().toList();
    }

    @Override
    public void add(Weather weather){
        weatherMap.put(weather.getId(), weather);
    }

    @Override
    public Weather getWeather(String regionName, LocalDate date) {
        return getAll().stream()
                .filter(x -> x.getRegionName().equals(regionName))
                .filter(x -> x.getDate().equals(date))
                .findFirst().orElseThrow();
    }

    @Override
    public Weather getWeatherById(UUID id) {
        return weatherMap.get(id);
    }

    @Override
    public void delete(UUID id) {
        weatherMap.remove(id);
    }

    @Override
    public void update(Weather weather) {
        weatherMap.replace(weather.getId(), weather);
    }

    public boolean exists(UUID id){
        return weatherMap.containsKey(id);
    }

    private Map<UUID, Weather> generateWeather() { //пока что так
        final int TEMPERATURE_SPREAD = 50;
        Random random = new Random();
        var newWeathers = new HashMap<UUID, Weather>();

        newWeathers.put(UUID.fromString("cc241865-2903-4af0-9da1-0306e1f6e928"),
                Weather.builder()
                        .id(UUID.fromString("cc241865-2903-4af0-9da1-0306e1f6e928"))
                        .regionId(UUID.fromString("f140f6d2-ec7f-456e-9069-2ed463acdf28"))
                        .regionName("Saint-Petersburg")
                        .date(LocalDate.now())
                        .temperature(random.nextDouble(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .build());
        newWeathers.put(UUID.fromString("72e1e340-b459-4d4e-9364-782d529d93c4"),
                Weather.builder()
                        .id(UUID.fromString("72e1e340-b459-4d4e-9364-782d529d93c4"))
                        .regionId(UUID.fromString("025afbe0-251e-4242-acdb-571c7aef9b84"))
                        .regionName("Moscow")
                        .date(LocalDate.now())
                        .temperature(random.nextDouble(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .build());
        newWeathers.put(UUID.fromString("c4be61d8-e5ef-4567-bde3-7c14f8a321e1"),
                Weather.builder()
                        .id(UUID.fromString("c4be61d8-e5ef-4567-bde3-7c14f8a321e1"))
                        .regionId(UUID.fromString("931f1ec4-36fd-4d2a-b3fd-2ac5d62492df"))
                        .regionName("Berdsk")
                        .date(LocalDate.now())
                        .temperature(random.nextDouble(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .build());
        newWeathers.put(UUID.fromString("915fb75e-f17b-48e2-9bb8-434d124cb35c"),
                Weather.builder()
                        .id(UUID.fromString("915fb75e-f17b-48e2-9bb8-434d124cb35c"))
                        .regionId(UUID.fromString("92f3adc6-b6d4-4177-8280-84b8c9460ff4"))
                        .regionName("Novosibirsk")
                        .date(LocalDate.now())
                        .temperature(random.nextDouble(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .build());
        newWeathers.put(UUID.fromString("5158a394-9dd8-4549-80a1-743fa8ef92f8"),
                Weather.builder()
                        .id(UUID.fromString("5158a394-9dd8-4549-80a1-743fa8ef92f8"))
                        .regionId(UUID.fromString("f140f6d2-ec7f-456e-9069-2ed463acdf28"))
                        .regionName("Saint-Petersburg")
                        .date(LocalDate.now().minusDays(1))
                        .temperature(random.nextDouble(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .build());
        newWeathers.put(UUID.fromString("fdc208fb-3a21-481d-95f9-926ec2349b9c"),
                Weather.builder()
                        .id(UUID.fromString("fdc208fb-3a21-481d-95f9-926ec2349b9c"))
                        .regionId(UUID.fromString("f140f6d2-ec7f-456e-9069-2ed463acdf28"))
                        .regionName("Saint-Petersburg")
                        .date(LocalDate.now().minusDays(2))
                        .temperature(random.nextDouble(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .build());
        newWeathers.put(UUID.fromString("adc208fb-3a21-481d-95f9-926ec2349b9c"),
                Weather.builder()
                        .id(UUID.fromString("adc208fb-3a21-481d-95f9-926ec2349b9c"))
                        .regionId(UUID.fromString("f140f6d2-ec7f-456e-9069-2ed463acdf28"))
                        .regionName("Saint-Petersburg")
                        .date(LocalDate.now().minusDays(3))
                        .temperature(random.nextDouble(-TEMPERATURE_SPREAD, TEMPERATURE_SPREAD))
                        .build());

        return newWeathers;
    }
}
