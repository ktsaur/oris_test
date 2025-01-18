package ru.kpfu.oris_test;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.kpfu.oris_test.client.HttpClientImpl;
import ru.kpfu.oris_test.services.ExchangeRateService;
import ru.kpfu.oris_test.services.WeatherService;

public class ChatBotController {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;

    private final WeatherService weatherService = new WeatherService(new HttpClientImpl());
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(new HttpClientImpl());


    @FXML
    private void handleCommand() {
        String userInput = inputField.getText().trim();
        inputField.clear();
        if (userInput.isEmpty()) return;

        chatArea.appendText("Вы: " + userInput + "\n");

        String[] parts = userInput.split(" ");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "list":
                showCommandList();
                break;
            case "weather":
                handleWeatherCommand(parts);
                break;
            case "exchange":
                handleExchangeRateCommand(parts);
                break;
            case "quit":
                quitToMainPage();
                break;
            default:
                chatArea.appendText("Бот: Неизвестная команда. Введите 'list' для списка команд.\n");
        }
    }

    private void showCommandList() {
        chatArea.appendText("Бот: Список доступных команд:\n");
        chatArea.appendText("  - list: показать список команд\n");
        chatArea.appendText("  - weather [город]: показать погоду в указанном городе\n");
        chatArea.appendText("  - exchange [валюта]: показать курс валюты к рублю\n");
        chatArea.appendText("  - quit: вернуться на главную страницу\n");
    }

    private void handleWeatherCommand(String[] parts) {
        if (parts.length < 2) {
            chatArea.appendText("Бот: Укажите город. Пример: weather Москва\n");
            return;
        }
        String city = parts[1];
        chatArea.appendText("Бот: " + weatherService.getWeather(city) + "\n");
    }

    private void handleExchangeRateCommand(String[] parts) {
        if (parts.length < 2) {
            chatArea.appendText("Бот: Укажите валюту. Пример: exchange USD\n");
            return;
        }
        String currency = parts[1];
        chatArea.appendText("Бот: " + exchangeRateService.getExchangeRate(currency) + "\n");
    }


    private void quitToMainPage() {
        chatArea.appendText("Бот: Вы вернулись на главную страницу.\n");
    }
}