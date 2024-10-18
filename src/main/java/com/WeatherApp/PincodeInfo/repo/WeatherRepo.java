package com.WeatherApp.PincodeInfo.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WeatherApp.PincodeInfo.model.WeatherData;

@Repository
public interface WeatherRepo extends JpaRepository<WeatherData, Long> {
}
