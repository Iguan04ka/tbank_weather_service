import java.util.Random;

public class Weather {
    private final Random random = new Random();
    private final int temperature;
    private final int humidity;
    private final int windSpeed;

    public Weather() {
        this.temperature = random.nextInt(-30, 50);
        this.humidity = random.nextInt(0, 100);
        this.windSpeed = random.nextInt(0, 30);
    }

    @Override
    public String toString() {
        return "Температура воздуха = " + temperature + "°C, " +
                "влажность = " + humidity + "%, " +
                "скорость ветра = " + windSpeed + " м/с";
    }
}
