package pl.pajwoj.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import pl.pajwoj.dtos._7TimerDTO;
import pl.pajwoj.exceptions._7TimerDataException;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class _7TimerDataService {
    public static ArrayList<DayWeather> get(Location location) throws _7TimerDataException {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<DayWeather> result = new ArrayList<>();

        String link = "https://www.7timer.info/bin/civil.php?lon=" +
                location.getLon() +
                "&lat=" +
                location.getLat() +
                "&ac=0&unit=metric&output=json&tzshift=0";

        Gson gson = new Gson();
        _7TimerDTO data;

        System.out.println("7Timer raw JSON: " + link);

        try {
            data = gson.fromJson((objectMapper.readTree(new URL(link)).toString()), _7TimerDTO.class);
            System.out.println("Got 7Timer data, parsing...");
        } catch (Exception e) {
            throw new _7TimerDataException("Error when getting data from 7Timer");
        }

        int iterator = 0;
        result.add(new DayWeather(location));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");

        LocalDateTime initDateTime = LocalDateTime.parse(data.getInit(), formatter);
        result.get(0).date(initDateTime.toLocalDate());

        for (int i = 0; i < data.getDataseries().size(); i++) {
            initDateTime = initDateTime.plusHours(3);

            if (initDateTime.toLocalDate().isEqual(result.get(iterator).getDate())) {
                result.get(iterator)
                        .newTime(initDateTime.toLocalTime())
                        .newTemp((Double) data.getDataseries().get(i).get("temp2m"))
                        .newPrecipitation((String) data.getDataseries().get(i).get("prec_type"));

            } else {
                _7TimerDataService.calculatePrecipitationChance(result.get(iterator));

                iterator++;
                _7TimerDataService.newWeatherObjectSetup(result, location, initDateTime, data, i);
            }
        }

        System.out.println("7Timer done... " + result);

        return result;
    }

    private static void newWeatherObjectSetup(ArrayList<DayWeather> result, Location location, LocalDateTime firstDateTime, _7TimerDTO data, int i) {
        DayWeather newWeather = new DayWeather(location);
        result.add(newWeather);

        newWeather
                .date(firstDateTime.toLocalDate())
                .newTime(firstDateTime.toLocalTime())
                .newTemp((Double) data.getDataseries().get(i).get("temp2m"))
                .newPrecipitation((String) data.getDataseries().get(i).get("prec_type"));
    }

    private static void calculatePrecipitationChance(DayWeather current) {
        HashSet<String> precipitationSet = new HashSet<>(current.getPrecipitation());

        if (precipitationSet.equals(new HashSet<>(Collections.singletonList("none"))))
            current.precipitationChance(-0.06);

        else
            current.precipitationChance(100.06);
    }
}
