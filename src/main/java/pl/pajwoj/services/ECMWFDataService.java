package pl.pajwoj.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import pl.pajwoj.dtos.ECMWF_DTO;
import pl.pajwoj.exceptions.ECMWFDataException;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ECMWFDataService {
    public static ArrayList<DayWeather> get(Location location) throws ECMWFDataException {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<DayWeather> result = new ArrayList<>();

        String link =
                "https://api.open-meteo.com/v1/ecmwf?latitude=" +
                        location.getLat() +
                        "&longitude=" +
                        location.getLon() +
                        "&hourly=temperature_2m,precipitation";

        Gson gson = new Gson();
        ECMWF_DTO data;

        System.out.println("ECMWF raw JSON: " + link);

        try {
            data = gson.fromJson((objectMapper.readTree(new URL(link)).toString()), ECMWF_DTO.class);
            System.out.println("Got ECMWF data, parsing...");
        } catch (Exception e) {
            throw new ECMWFDataException("Error when getting data from ECMWF");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        int iterator = 0;
        result.add(new DayWeather(location));

        LocalDateTime initDateTime = LocalDateTime.parse(data.getHourly().get("time").get(0).toString(), formatter);
        result.get(0).date(initDateTime.toLocalDate());

        for (int i = 0; i < data.getHourly().get("time").size(); i++) {
            initDateTime = initDateTime.plusHours(3);

            if (initDateTime.toLocalDate().isEqual(result.get(iterator).getDate())) {
                try {
                    result.get(iterator)
                            .newTime(initDateTime.toLocalTime())
                            .newTemp(Double.parseDouble(data.getHourly().get("temperature_2m").get(i).toString()))
                            .newPrecipitation(data.getHourly().get("precipitation").get(i).toString());

                } catch (NullPointerException e) {
                    result.get(iterator)
                            .newTemp(0.0)
                            .newPrecipitation("0");
                }

            } else {
                ECMWFDataService.calculatePrecipitationChance(result.get(iterator));

                iterator++;
                ECMWFDataService.newWeatherObjectSetup(result, location, initDateTime, data, i);
            }
        }

        System.out.println("ECMWF done... " + result);

        return result;
    }

    private static void newWeatherObjectSetup(ArrayList<DayWeather> result, Location location, LocalDateTime initDateTime, ECMWF_DTO data, int i) {
        DayWeather newWeather = new DayWeather(location);
        result.add(newWeather);

        try {
            newWeather
                    .date(initDateTime.toLocalDate())
                    .newTime(initDateTime.toLocalTime())
                    .newTemp(Double.parseDouble(data.getHourly().get("temperature_2m").get(i).toString()))
                    .newPrecipitation(data.getHourly().get("precipitation").get(i).toString());

        } catch (NullPointerException e) {
            newWeather
                    .newTemp(0.0)
                    .newPrecipitation("0");
        }

    }

    private static void calculatePrecipitationChance(DayWeather current) {
        HashSet<String> precipitationSet = new HashSet<>(current.getPrecipitation());

        if (precipitationSet.equals(new HashSet<>(Collections.singletonList("0.0"))))
            current.precipitationChance(-0.06);

        else
            current.precipitationChance(100.06);
    }
}
