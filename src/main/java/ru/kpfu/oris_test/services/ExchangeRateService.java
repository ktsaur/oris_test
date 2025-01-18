package ru.kpfu.oris_test.services;

import org.cloudinary.json.JSONObject;
import ru.kpfu.oris_test.client.HttpClient;

import java.util.Map;

public class ExchangeRateService {
    private static final String API_URL = "https://www.cbr-xml-daily.ru/daily_json.js";
    private final HttpClient httpClient;

    public ExchangeRateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getExchangeRate(String currency) {
        try {
            String response = httpClient.get(API_URL, Map.of(), Map.of());
            return parseExchangeRate(response, currency);
        } catch (Exception e) {
            return "Ошибка: не удалось получить данные о курсе валют.";
        }
    }

    private String parseExchangeRate(String json, String currency) {
        JSONObject jsonObject = new JSONObject(json).getJSONObject("Valute");
        if (jsonObject.has(currency.toUpperCase())) {
            double rate = jsonObject.getJSONObject(currency.toUpperCase()).getDouble("Value");
            return String.format("%.2f RUB", rate);
        } else {
            return "Валюта не найдена.";
        }
    }
}
