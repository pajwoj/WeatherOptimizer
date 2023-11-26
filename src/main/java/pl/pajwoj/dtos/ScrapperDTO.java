package pl.pajwoj.dtos;

import java.util.ArrayList;
import java.util.Map;

public class ScrapperDTO {
    private Map<String, Object> forecast;
    private ArrayList<Map<String, Object>> hourly;
    private ArrayList<Map<String, Object>> sunriseSunset;

    @SuppressWarnings("unchecked")
    public void parseForecast() {
        Map<String, Object> forecast2 = (Map<String, Object>) forecast.get("forecast");
        Map<String, Object> forecasts = (Map<String, Object>) forecast2.get("forecasts");

        hourly = (ArrayList<Map<String, Object>>) forecasts.get("hourly");
        sunriseSunset = (ArrayList<Map<String, Object>>) forecasts.get("sunriseSunset");
    }

    public ArrayList<Map<String, Object>> getHourly() {
        return hourly;
    }

    public ArrayList<Map<String, Object>> getSunriseSunset() {
        return sunriseSunset;
    }
}
