package ru.iguana.weatherService.data;

import ru.iguana.weatherService.model.City;

import java.util.*;

public class WeatherRepositoryImpl implements WeatherRepository {

    private final Map<String, City> cities = new HashMap<>();

    @Override
    public Collection<City> findAll() {
        return cities.values();
    }

    @Override
    public Optional<City> findOneByName(String name) {
        return Optional.ofNullable(cities.get(name));
    }

    @Override
    public City save(City city) {
        cities.put(city.getCityName(), city);
        return city;
    }

    @Override
    public long delete(City city) {
        return cities.remove(city.getCityName()) != null ? 1 : 0;
    }
}