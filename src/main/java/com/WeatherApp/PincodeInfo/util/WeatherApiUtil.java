package com.WeatherApp.PincodeInfo.util;

import java.time.LocalDate;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.WeatherApp.PincodeInfo.model.WeatherData;



public class WeatherApiUtil {
    private static final String API_KEY = "a4a0738e86e0bc0b7186af4e565faf36";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s";

    public static WeatherData getWeatherData(double lat, double lon, LocalDate forDate) {
        String url = String.format(WEATHER_URL, lat, lon, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonResponse = new JSONObject(response);

        WeatherData weatherData = new WeatherData();
        weatherData.setForDate(forDate);
        weatherData.setWeatherDescription(jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description"));
        weatherData.setTemperature(jsonResponse.getJSONObject("main").getDouble("temp") - 273.15); // Convert from Kelvin to Celsius
        return weatherData;
    }
}
