package fintech;

import fintech.services.WeatherService;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        final HashMap<UUID, String> regions = new HashMap<>();
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


        var weathers = WeatherService.generateWeatherList(regions);

        System.out.println("Calculate the average temperature in the regions.");
        System.out.println(WeatherService.calculateAverageTemperature(weathers));

        System.out.println();
        System.out.print("Create a function to search for regions greater than a certain temperature: ");
        Scanner input = new Scanner(System.in);
        int receivedTemperature= input.nextInt();
        var acceptableRegions = WeatherService.getRegionsWithHigherTemperature(weathers, receivedTemperature);
        if(acceptableRegions.isEmpty()) {
            System.out.println("No regions");
        }
        else {
            acceptableRegions.forEach(System.out::println);
        }

        System.out.println();
        System.out.println("Convert the list to a Map, whose key is a unique identifier, " +
                "the value is a list with temperature values.");
        var map = WeatherService.getRegionsTemperaturesMap(weathers).entrySet();
        map.forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));

        System.out.println();
        System.out.println("Convert the list to a Map whose key is temperature, the value is a collection of " +
                "Weather objects that correspond to the temperature specified in the key.");
        var temperatureMap = WeatherService.getTemperatureWeatherMap(weathers).entrySet();
        temperatureMap.forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));
    }
}