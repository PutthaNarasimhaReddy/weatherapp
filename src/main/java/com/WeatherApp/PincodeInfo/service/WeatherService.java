package com.WeatherApp.PincodeInfo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.WeatherApp.PincodeInfo.model.WeatherData;
import com.WeatherApp.PincodeInfo.repo.WeatherRepo;
import com.WeatherApp.PincodeInfo.util.GeocodingUtil;

import org.json.JSONObject;



@Service
public class WeatherService {

    private static final String OPENWEATHERMAP_API_KEY = "a4a0738e86e0bc0b7186af4e565faf36";

    @Autowired
    private WeatherRepo weatherDataRepository;

    public WeatherData getWeatherData(String pincode, LocalDate forDate) {
        double[] latLong = GeocodingUtil.getLatLongFromPincode(pincode);
        double latitude = latLong[0];
        double longitude = latLong[1];

        if (latitude == 0.0 && longitude == 0.0) {
            throw new RuntimeException("Invalid latitude or longitude for pincode: " + pincode);
        }

        String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s", 
                                    latitude, longitude, OPENWEATHERMAP_API_KEY);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonResponse = new JSONObject(response);
        
        String weatherDescription = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperature = jsonResponse.getJSONObject("main").getDouble("temp");

        WeatherData weatherData = new WeatherData();
        weatherData.setPincode(pincode);
        weatherData.setLatitude(latitude);
        weatherData.setLongitude(longitude);
        weatherData.setForDate(forDate);
        weatherData.setWeatherDescription(weatherDescription);
        weatherData.setTemperature(temperature - 273.15); 

        weatherDataRepository.save(weatherData); 

        return weatherData;
    }
}
