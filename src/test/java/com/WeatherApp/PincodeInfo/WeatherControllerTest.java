package com.WeatherApp.PincodeInfo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.WeatherApp.PincodeInfo.controller.WeatherController;
import com.WeatherApp.PincodeInfo.model.WeatherData;
import com.WeatherApp.PincodeInfo.service.WeatherService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;




@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetWeather() throws Exception {
        WeatherData mockWeatherData = new WeatherData();
        mockWeatherData.setPincode("411014");
        mockWeatherData.setForDate(LocalDate.of(2020, 10, 15));
        mockWeatherData.setWeatherDescription("Clear sky");
        mockWeatherData.setTemperature(25.0);

        Mockito.when(weatherService.getWeatherData("411014", LocalDate.of(2020, 10, 15)))
                .thenReturn(mockWeatherData);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather?pincode=411014&forDate=2020-10-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weatherDescription").value("Clear sky"))
                .andExpect(jsonPath("$.temperature").value(25.0));
    }
}