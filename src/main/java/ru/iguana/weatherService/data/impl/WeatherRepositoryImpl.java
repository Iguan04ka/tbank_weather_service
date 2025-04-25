package ru.iguana.weatherService.data.impl;

import ru.iguana.weatherService.data.ConnectionData;
import ru.iguana.weatherService.data.WeatherRepository;
import ru.iguana.weatherService.model.City;
import ru.iguana.weatherService.model.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class WeatherRepositoryImpl implements WeatherRepository {
    private final ConnectionData connectionData;

    public WeatherRepositoryImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public Collection<City> findAll() {
        Collection<City> cities = new ArrayList<>();
        String sql = "SELECT c.name, w.temperature, w.humidity, w.wind_speed " +
                "FROM city c " +
                "LEFT JOIN weather w ON c.id = w.city_id " +
                "WHERE w.measured_at = (SELECT MAX(measured_at) FROM weather WHERE city_id = c.id) " +
                "OR w.measured_at IS NULL";

        try (Connection conn = DriverManager.getConnection(connectionData.getUrl(), connectionData.getUser(), connectionData.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                City city = new City(rs.getString("name"));

                if (rs.getObject("temperature") != null) {
                    Weather weather = new Weather();
                    weather.setTemperature(rs.getInt("temperature"));
                    weather.setHumidity(rs.getInt("humidity"));
                    weather.setWindSpeed(rs.getInt("wind_speed"));
                    city.setWeather(weather);
                }
                cities.add(city);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch cities with weather", e);
        }
        return cities;
    }

    @Override
    public Optional<City> findOneByName(String name) {
        String sql = "SELECT c.name, w.temperature, w.humidity, w.wind_speed " +
                "FROM city c " +
                "LEFT JOIN weather w ON c.id = w.city_id " +
                "WHERE c.name = ? " +
                "ORDER BY w.measured_at DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(connectionData.getUrl(), connectionData.getUser(), connectionData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                City city = new City(rs.getString("name"));

                if (rs.getObject("temperature") != null) {
                    Weather weather = new Weather();
                    weather.setTemperature(rs.getInt("temperature"));
                    weather.setHumidity(rs.getInt("humidity"));
                    weather.setWindSpeed(rs.getInt("wind_speed"));
                    city.setWeather(weather);
                }
                return Optional.of(city);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch city: " + name, e);
        }
    }

    @Override
    public void save(City city) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionData.getUrl(), connectionData.getUser(), connectionData.getPassword());
            conn.setAutoCommit(false);

            // 1. Сохраняем город (если его нет)
            Integer cityId = saveCity(conn, city);

            // 2. Если есть погодные данные - сохраняем их
            if (cityId != null && city.getWeather() != null) {
                saveWeather(conn, cityId, city.getWeather());
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Failed to save city and weather", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Integer saveCity(Connection conn, City city) throws SQLException {
        // Сначала проверяем существование города
        String checkSql = "SELECT id FROM city WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkSql)) {
            stmt.setString(1, city.getCityName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        // Если города нет - вставляем
        String insertSql = "INSERT INTO city (name) VALUES (?) RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            stmt.setString(1, city.getCityName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return null;
    }

    private void saveWeather(Connection conn, int cityId, Weather weather) throws SQLException {
        String sql = "INSERT INTO weather " +
                "(city_id, temperature, humidity, wind_speed, measured_at) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cityId);
            stmt.setInt(2, weather.getTemperature());
            stmt.setInt(3, weather.getHumidity());
            stmt.setInt(4, weather.getWindSpeed());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(City city) {
        String sql = "DELETE FROM city WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(connectionData.getUrl(), connectionData.getUser(), connectionData.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city.getCityName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete city: " + city.getCityName(), e);
        }
    }
}