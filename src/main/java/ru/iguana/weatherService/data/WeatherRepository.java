package ru.iguana.weatherService.data;

import ru.iguana.weatherService.model.City;

import java.util.Collection;
import java.util.Optional;

public interface WeatherRepository {

    Collection<City> findAll();

    Optional<City> findOneByName(String name);

    void save(City city);

    void delete(City city);
}
