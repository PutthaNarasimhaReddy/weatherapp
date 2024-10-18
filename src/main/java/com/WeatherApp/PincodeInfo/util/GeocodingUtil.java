package com.WeatherApp.PincodeInfo.util;

import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;

public class GeocodingUtil {
    private static final String GEOCODING_URL = "https://nominatim.openstreetmap.org/search?format=json&postalcode=%s&country=India";

    public static double[] getLatLongFromPincode(String pincode) {
        String url = String.format(GEOCODING_URL, pincode);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        System.out.println("Nominatim API Response: " + response);

        JSONArray jsonArray = new JSONArray(response);
        if (jsonArray.length() == 0) {
            throw new RuntimeException("No results found for pincode: " + pincode);
        }

        double latitude = jsonArray.getJSONObject(0).getDouble("lat");
        double longitude = jsonArray.getJSONObject(0).getDouble("lon");

        return new double[]{latitude, longitude};
    }
}
