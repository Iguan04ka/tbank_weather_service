package ru.iguana.weatherService.service;

import ru.iguana.weatherService.data.WeatherRepository;
import ru.iguana.weatherService.exeptions.CityNotFoundException;
import ru.iguana.weatherService.model.City;

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
                .orElseThrow(() -> new CityNotFoundException(name));
    }

    @Override
    public void create(String name) {
        City city = new City(name);
        repository.save(city);
    }

    @Override
    public void delete(String name) {
        City city = findOneByName(name);
        repository.delete(city);
    }
}
