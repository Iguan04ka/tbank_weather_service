package ru.iguana.weatherService.exceptions;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String message) {
        super("Город с названием " + message + " не найден");
    }
}
