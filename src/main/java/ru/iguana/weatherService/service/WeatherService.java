package ru.iguana.weatherService.service;

import ru.iguana.weatherService.model.City;

import java.util.Collection;

public interface WeatherService {

    Collection<City> findAll();

    City findOneByName(String name);

    void create(String name);

    void delete(String name);
}
