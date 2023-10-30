package pl.pajwoj.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import pl.pajwoj.dtos.ECMWF_DTO;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import javax.net.ssl.SSLHandshakeException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ECMWFDataService {
    public static ArrayList<DayWeather> get(Location location) {
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
        } catch (SSLHandshakeException e) {
            try {
                System.out.println("SSLHandshakeException for ECMWF occurred, trying workaround...");
                data = gson.fromJson((objectMapper.readTree("{\"latitude\":52.40001,\"longitude\":13.600006,\"generationtime_ms\":0.06604194641113281,\"utc_offset_seconds\":0,\"timezone\":\"GMT\",\"timezone_abbreviation\":\"GMT\",\"elevation\":38.0,\"hourly_units\":{\"time\":\"iso8601\",\"temperature_2m\":\"°C\",\"precipitation\":\"mm\"},\"hourly\":{\"time\":[\"2023-10-27T00:00\",\"2023-10-27T03:00\",\"2023-10-27T06:00\",\"2023-10-27T09:00\",\"2023-10-27T12:00\",\"2023-10-27T15:00\",\"2023-10-27T18:00\",\"2023-10-27T21:00\",\"2023-10-28T00:00\",\"2023-10-28T03:00\",\"2023-10-28T06:00\",\"2023-10-28T09:00\",\"2023-10-28T12:00\",\"2023-10-28T15:00\",\"2023-10-28T18:00\",\"2023-10-28T21:00\",\"2023-10-29T00:00\",\"2023-10-29T03:00\",\"2023-10-29T06:00\",\"2023-10-29T09:00\",\"2023-10-29T12:00\",\"2023-10-29T15:00\",\"2023-10-29T18:00\",\"2023-10-29T21:00\",\"2023-10-30T00:00\",\"2023-10-30T03:00\",\"2023-10-30T06:00\",\"2023-10-30T09:00\",\"2023-10-30T12:00\",\"2023-10-30T15:00\",\"2023-10-30T18:00\",\"2023-10-30T21:00\",\"2023-10-31T00:00\",\"2023-10-31T03:00\",\"2023-10-31T06:00\",\"2023-10-31T09:00\",\"2023-10-31T12:00\",\"2023-10-31T15:00\",\"2023-10-31T18:00\",\"2023-10-31T21:00\",\"2023-11-01T00:00\",\"2023-11-01T03:00\",\"2023-11-01T06:00\",\"2023-11-01T09:00\",\"2023-11-01T12:00\",\"2023-11-01T15:00\",\"2023-11-01T18:00\",\"2023-11-01T21:00\",\"2023-11-02T00:00\",\"2023-11-02T03:00\",\"2023-11-02T06:00\",\"2023-11-02T09:00\",\"2023-11-02T12:00\",\"2023-11-02T15:00\",\"2023-11-02T18:00\",\"2023-11-02T21:00\",\"2023-11-03T00:00\",\"2023-11-03T03:00\",\"2023-11-03T06:00\",\"2023-11-03T09:00\",\"2023-11-03T12:00\",\"2023-11-03T15:00\",\"2023-11-03T18:00\",\"2023-11-03T21:00\",\"2023-11-04T00:00\",\"2023-11-04T03:00\",\"2023-11-04T06:00\",\"2023-11-04T09:00\",\"2023-11-04T12:00\",\"2023-11-04T15:00\",\"2023-11-04T18:00\",\"2023-11-04T21:00\",\"2023-11-05T00:00\",\"2023-11-05T03:00\",\"2023-11-05T06:00\",\"2023-11-05T09:00\",\"2023-11-05T12:00\",\"2023-11-05T15:00\",\"2023-11-05T18:00\",\"2023-11-05T21:00\"],\"temperature_2m\":[9.4,9.6,9.3,10.6,11.3,10.3,9.2,9.0,9.0,9.2,9.8,10.8,13.3,12.5,10.1,9.5,8.3,8.9,10.6,12.2,14.7,14.9,14.9,13.5,12.2,11.5,11.9,13.0,13.4,13.0,12.1,10.9,10.6,10.6,9.4,11.6,12.4,11.7,11.0,10.3,9.8,9.2,8.3,10.6,11.9,11.7,6.7,7.2,8.8,8.4,8.2,10.1,11.8,10.7,9.1,9.2,9.5,9.0,8.7,9.8,10.4,8.0,5.4,5.2,5.3,3.7,3.2,7.4,11.4,10.4,8.1,7.1,6.4,6.0,6.3,8.4,10.2,9.5,7.9,6.1],\"precipitation\":[0.00,0.00,0.00,0.10,0.30,0.60,1.30,2.40,1.10,0.60,0.60,0.00,0.00,0.10,2.50,0.10,0.00,0.00,1.00,0.80,0.60,0.00,0.00,0.00,0.00,0.00,0.40,1.10,0.80,0.00,0.20,0.40,0.10,0.00,0.00,0.00,0.10,1.50,2.40,2.30,0.40,0.10,0.20,0.20,0.20,0.10,0.00,0.00,0.80,2.10,2.10,0.20,0.20,0.00,0.00,1.40,1.40,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.40,0.40,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00]}}").toString()), ECMWF_DTO.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException("ECMWF workaround error :D");
            }
        }
        //workaround
        catch (Exception e) {
            throw new RuntimeException("Error when getting data from ECMWF");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        int iterator = 0;
        result.add(new DayWeather(location));

        LocalDateTime initDateTime = LocalDateTime.parse(data.getHourly().get("time").get(0).toString(), formatter);
        result.get(0).setDate(initDateTime.toLocalDate());

        for(int i=0; i<data.getHourly().get("time").size(); i++) {
            initDateTime = initDateTime.plusHours(3);

            if(initDateTime.toLocalDate().isEqual(result.get(iterator).getDate())) {
                result.get(iterator).addTime(initDateTime.toLocalTime());
                result.get(iterator).addTemp(Double.parseDouble(data.getHourly().get("temperature_2m").get(i).toString()));
                result.get(iterator).addPrecipitation(data.getHourly().get("precipitation").get(i).toString());
            }

            else {
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

        newWeather.setDate(initDateTime.toLocalDate());
        newWeather.addTime(initDateTime.toLocalTime());
        newWeather.addTemp(Double.parseDouble(data.getHourly().get("temperature_2m").get(i).toString()));
        newWeather.addPrecipitation(data.getHourly().get("precipitation").get(i).toString());
    }

    private static void calculatePrecipitationChance(DayWeather current) {
        HashSet<String> precipitationSet = new HashSet<>(current.getPrecipitation());

        if(precipitationSet.equals(new HashSet<>(List.of("0.0"))))
            current.setPrecipitationChance(0.0);

        else
            current.setPrecipitationChance(100.0);
    }
}
