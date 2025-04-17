package ru.iguana.weatherService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, City> cities = new HashMap<>();
        boolean flag = true;

        Scanner scanner = new Scanner(System.in);

        while (flag) {
            System.out.println("Введите число (0 - выход, " +
                    "1 - узнать погоду в городе, " +
                    "2 - узнать погоду во всех сохраненных городах): ");
            short option = scanner.nextShort();

            switch (option) {
                case 0 -> {
                    System.out.println("Выход из программы.");
                    flag = false;
                }

                case 1 -> {
                    System.out.println("Введите название города:");
                    scanner.nextLine();

                    String cityName = scanner.nextLine();

                    if (cities.containsKey(cityName)){
                        System.out.println("Погода в городе " + cities.get(cityName));
                    }
                    else {
                        City city = new City(cityName);
                        cities.put(city.getCityName(), city);
                        System.out.println("Погода в городе " + city);
                    }
                }

                case 2 -> {
                    for (Map.Entry<String, City> entry : cities.entrySet()) {
                        System.out.println("Погода в городе " + entry.getValue());
                    }
                }

                default -> System.out.println("Неверный ввод. Пожалуйста, введите 0, 1 или 2.");
            }
        }
        scanner.close();
    }
}

