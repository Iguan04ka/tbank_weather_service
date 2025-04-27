package ru.iguana.weatherService.service.impl;

import ru.iguana.weatherService.data.WeatherRepository;
import ru.iguana.weatherService.model.City;
import ru.iguana.weatherService.service.WeatherService;

import java.util.Collection;

public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository repository;

    public WeatherServiceImpl(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<City> findAll() {
        return repository.findAll();
    }

    @Override
    public City findOneByName(String name) {
        return repository.findOneByName(name)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + name));
    }

    @Override
    public void create(String name) {
        repository.save(new City(name));
    }

    @Override
    public void delete(String name) {
        repository.delete(new City(name));
    }
}