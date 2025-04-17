package ru.iguana.weatherService.exeptions;

public class IllegalCityNameException extends RuntimeException {
    public IllegalCityNameException(String cityName) {
        super("Некорректное название города: \"" + cityName + "\"");
    }
}
