import exeptions.IllegalCityNameException;

public class City {
    private String cityName;
    private Weather weather = new Weather();

    public City(String cityName) {
        if (validateCityName(cityName)) {
            this.cityName = cityName;
        }
    }

    private boolean validateCityName(String cityName){
        if (cityName == null || cityName.trim().isEmpty() || !cityName.matches("[А-Яа-яЁё\\s-]+")) {
            throw new IllegalCityNameException(cityName);
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
