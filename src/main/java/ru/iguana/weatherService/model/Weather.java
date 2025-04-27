package ru.iguana.weatherService.model;

import java.util.Random;

public class Weather {
    private int temperature;
    private int humidity;
    private int windSpeed;

    public Weather() {
        Random random = new Random();
        this.temperature = random.nextInt(-30, 50);
        this.humidity = random.nextInt(0, 100);
        this.windSpeed = random.nextInt(0, 30);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "Температура воздуха = " + temperature + "°C, " +
                "влажность = " + humidity + "%, " +
                "скорость ветра = " + windSpeed + " м/с";
    }
}
