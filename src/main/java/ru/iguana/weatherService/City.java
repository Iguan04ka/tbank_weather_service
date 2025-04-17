package ru.iguana.weatherService;

import ru.iguana.weatherService.exeptions.IllegalCityNameException;

public class City {
    private String cityName;
    private Weather weather;

    public City(String cityName) {
        this.weather = new Weather();

        if (validateCityName(cityName)) {
            this.cityName = cityName;
        }
        else throw new IllegalCityNameException(cityName);
    }

    private boolean validateCityName(String cityName){
        if (cityName == null || cityName.trim().isEmpty() || !cityName.matches("[А-Яа-яЁё\\s-]+")) {
            return false;
        }
        return true;
    }

    public String getCityName() {
        return cityName;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setCityName(String cityName) {
        if (validateCityName(cityName)) {
            this.cityName = cityName;
        }
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return cityName + ": " + weather;
    }
}
