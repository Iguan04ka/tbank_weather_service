package ru.iguana.weatherService.exeptions;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String message) {
        super("Город с названием " + message + " не найден");
    }
}
