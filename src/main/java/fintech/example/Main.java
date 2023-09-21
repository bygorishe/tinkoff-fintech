package fintech.example;

import fintech.example.logic.Logic;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Logic logic = new Logic();
        var t = logic.getWeathersList();

        System.out.println("Calculate the average temperature in the regions.");
        System.out.println(logic.calculateAverageTemperature());

        System.out.println();
        System.out.print("Create a function to search for regions greater than a certain temperature: ");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        var regions = logic.getRegionsWithHigherTemperature(num);
        if(regions.isEmpty()) {
            System.out.println("No regions");
        }
        else {
            regions.forEach(System.out::println);
        }

        System.out.println();
        System.out.println("Convert the list to a Map, whose key is a unique identifier, " +
                "the value is a list with temperature values.");
        var map = logic.getRegionsTemperaturesMap().entrySet();
        map.forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));

        System.out.println();
        System.out.println("Convert the list to a Map whose key is temperature, the value is a collection of " +
                "Weather objects that correspond to the temperature specified in the key.");
        var temperatureMap = logic.getTemperatureWeatherMap().entrySet();
        temperatureMap.forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));
    }
}