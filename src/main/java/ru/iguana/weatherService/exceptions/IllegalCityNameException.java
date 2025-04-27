package ru.iguana.weatherService.exceptions;

public class IllegalCityNameException extends RuntimeException {
    public IllegalCityNameException(String cityName) {
        super("Некорректное название города: \"" + cityName + "\"");
    }
}
