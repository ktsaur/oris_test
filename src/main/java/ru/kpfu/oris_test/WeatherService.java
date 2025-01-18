package ru.kpfu.oris_test;


import org.cloudinary.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeatherService {
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "bd5e378503939ddaee76f12ad7a97608";
    private final HttpClient httpClient;

    public WeatherService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getWeather(String city) {
        Map<String, String> params = new HashMap<>();
        params.put("q", city);
        params.put("appid", API_KEY);
        params.put("units", "metric");
        params.put("lang", "ru");

        try {
            String response = httpClient.get(API_URL, Map.of(), params);
            return parseWeather(response, city);
        } catch (Exception e) {
            return "Ошибка: не удалось получить данные о погоде.";
        }
    }

    private String parseWeather(String json, String city) {
        JSONObject jsonObject = new JSONObject(json);
        String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        return String.format("Погода в %s: %s, %.1f°C", city, description, temperature);
    }
}