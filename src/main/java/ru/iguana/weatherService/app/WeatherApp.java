package ru.iguana.weatherService.app;

import ru.iguana.weatherService.data.LiquibaseChangelogLoader;
import ru.iguana.weatherService.data.WeatherRepository;
import ru.iguana.weatherService.data.impl.WeatherRepositoryImpl;
import ru.iguana.weatherService.exeptions.CityNotFoundException;
import ru.iguana.weatherService.exeptions.IllegalCityNameException;
import ru.iguana.weatherService.model.City;
import ru.iguana.weatherService.service.WeatherService;
import ru.iguana.weatherService.service.WeatherServiceImpl;

import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        LiquibaseChangelogLoader.load();

        WeatherRepository weatherRepository = new WeatherRepositoryImpl();
        WeatherService service = new WeatherServiceImpl(weatherRepository);

        Scanner scanner = new Scanner(System.in);

        boolean flag = true;

        while (flag) {
            System.out.println("\nВведите число:\n" +
                    "0 - выход\n" +
                    "1 - узнать погоду в городе\n" +
                    "2 - показать погоду во всех сохранённых городах\n" +
                    "3 - удалить город");
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

                    try {
                        City city;
                        try {
                            city = service.findOneByName(cityName);
                        }
                        catch (CityNotFoundException e) {
                            service.create(cityName);
                            city = service.findOneByName(cityName);
                        }

                        System.out.println("Погода в городе " + city);
                    }
                    catch (IllegalCityNameException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 2 -> {
                    for (City city : service.findAll()) {
                        System.out.println("Погода в городе " + city);
                    }
                }

                case 3 -> {
                    System.out.println("Введите название города для удаления:");
                    scanner.nextLine();

                    String cityName = scanner.nextLine();

                    try {
                        service.delete(cityName);
                        System.out.println("Город \"" + cityName + "\" успешно удалён.");
                    }
                    catch (CityNotFoundException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                }

                default -> System.out.println("Неверный ввод. Пожалуйста, введите 0, 1 или 2.");
            }
        }

        scanner.close();
    }
}
