package pl.pajwoj.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import pl.pajwoj.dtos.OpenMeteoDTO;
import pl.pajwoj.models.OpenMeteoDayWeather;
import pl.pajwoj.models.Location;

import javax.net.ssl.SSLHandshakeException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OpenMeteoDataService {
    public static ArrayList<OpenMeteoDayWeather> get(Location location) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<OpenMeteoDayWeather> result = new ArrayList<>();

        String link =
                "https://api.open-meteo.com/v1/forecast?latitude=" +
                        location.getLat() +
                        "&longitude=" +
                        location.getLon() +
                        "&hourly=temperature_2m,precipitation_probability&daily=sunrise,sunset&timezone=Europe%2FBerlin";

        Gson gson = new Gson();
        OpenMeteoDTO data;

        System.out.println("OpenMeteo raw JSON: " + link);

        try {
            data = gson.fromJson((objectMapper.readTree(new URL(link)).toString()), OpenMeteoDTO.class);
            System.out.println("Got OpenMeteo data, parsing...");
        } catch (SSLHandshakeException e) {
            try {
                System.out.println("SSLHandshakeException for OpenMeteo occurred, trying workaround...");
                data = gson.fromJson(objectMapper.readTree("{\"latitude\":50.0625,\"longitude\":20.0,\"generationtime_ms\":0.21004676818847656,\"utc_offset_seconds\":7200,\"timezone\":\"Europe/Berlin\",\"timezone_abbreviation\":\"CEST\",\"elevation\":201.0,\"hourly_units\":{\"time\":\"iso8601\",\"temperature_2m\":\"°C\",\"precipitation_probability\":\"%\"},\"hourly\":{\"time\":[\"2023-10-26T00:00\",\"2023-10-26T01:00\",\"2023-10-26T02:00\",\"2023-10-26T03:00\",\"2023-10-26T04:00\",\"2023-10-26T05:00\",\"2023-10-26T06:00\",\"2023-10-26T07:00\",\"2023-10-26T08:00\",\"2023-10-26T09:00\",\"2023-10-26T10:00\",\"2023-10-26T11:00\",\"2023-10-26T12:00\",\"2023-10-26T13:00\",\"2023-10-26T14:00\",\"2023-10-26T15:00\",\"2023-10-26T16:00\",\"2023-10-26T17:00\",\"2023-10-26T18:00\",\"2023-10-26T19:00\",\"2023-10-26T20:00\",\"2023-10-26T21:00\",\"2023-10-26T22:00\",\"2023-10-26T23:00\",\"2023-10-27T00:00\",\"2023-10-27T01:00\",\"2023-10-27T02:00\",\"2023-10-27T03:00\",\"2023-10-27T04:00\",\"2023-10-27T05:00\",\"2023-10-27T06:00\",\"2023-10-27T07:00\",\"2023-10-27T08:00\",\"2023-10-27T09:00\",\"2023-10-27T10:00\",\"2023-10-27T11:00\",\"2023-10-27T12:00\",\"2023-10-27T13:00\",\"2023-10-27T14:00\",\"2023-10-27T15:00\",\"2023-10-27T16:00\",\"2023-10-27T17:00\",\"2023-10-27T18:00\",\"2023-10-27T19:00\",\"2023-10-27T20:00\",\"2023-10-27T21:00\",\"2023-10-27T22:00\",\"2023-10-27T23:00\",\"2023-10-28T00:00\",\"2023-10-28T01:00\",\"2023-10-28T02:00\",\"2023-10-28T03:00\",\"2023-10-28T04:00\",\"2023-10-28T05:00\",\"2023-10-28T06:00\",\"2023-10-28T07:00\",\"2023-10-28T08:00\",\"2023-10-28T09:00\",\"2023-10-28T10:00\",\"2023-10-28T11:00\",\"2023-10-28T12:00\",\"2023-10-28T13:00\",\"2023-10-28T14:00\",\"2023-10-28T15:00\",\"2023-10-28T16:00\",\"2023-10-28T17:00\",\"2023-10-28T18:00\",\"2023-10-28T19:00\",\"2023-10-28T20:00\",\"2023-10-28T21:00\",\"2023-10-28T22:00\",\"2023-10-28T23:00\",\"2023-10-29T00:00\",\"2023-10-29T01:00\",\"2023-10-29T02:00\",\"2023-10-29T03:00\",\"2023-10-29T04:00\",\"2023-10-29T05:00\",\"2023-10-29T06:00\",\"2023-10-29T07:00\",\"2023-10-29T08:00\",\"2023-10-29T09:00\",\"2023-10-29T10:00\",\"2023-10-29T11:00\",\"2023-10-29T12:00\",\"2023-10-29T13:00\",\"2023-10-29T14:00\",\"2023-10-29T15:00\",\"2023-10-29T16:00\",\"2023-10-29T17:00\",\"2023-10-29T18:00\",\"2023-10-29T19:00\",\"2023-10-29T20:00\",\"2023-10-29T21:00\",\"2023-10-29T22:00\",\"2023-10-29T23:00\",\"2023-10-30T00:00\",\"2023-10-30T01:00\",\"2023-10-30T02:00\",\"2023-10-30T03:00\",\"2023-10-30T04:00\",\"2023-10-30T05:00\",\"2023-10-30T06:00\",\"2023-10-30T07:00\",\"2023-10-30T08:00\",\"2023-10-30T09:00\",\"2023-10-30T10:00\",\"2023-10-30T11:00\",\"2023-10-30T12:00\",\"2023-10-30T13:00\",\"2023-10-30T14:00\",\"2023-10-30T15:00\",\"2023-10-30T16:00\",\"2023-10-30T17:00\",\"2023-10-30T18:00\",\"2023-10-30T19:00\",\"2023-10-30T20:00\",\"2023-10-30T21:00\",\"2023-10-30T22:00\",\"2023-10-30T23:00\",\"2023-10-31T00:00\",\"2023-10-31T01:00\",\"2023-10-31T02:00\",\"2023-10-31T03:00\",\"2023-10-31T04:00\",\"2023-10-31T05:00\",\"2023-10-31T06:00\",\"2023-10-31T07:00\",\"2023-10-31T08:00\",\"2023-10-31T09:00\",\"2023-10-31T10:00\",\"2023-10-31T11:00\",\"2023-10-31T12:00\",\"2023-10-31T13:00\",\"2023-10-31T14:00\",\"2023-10-31T15:00\",\"2023-10-31T16:00\",\"2023-10-31T17:00\",\"2023-10-31T18:00\",\"2023-10-31T19:00\",\"2023-10-31T20:00\",\"2023-10-31T21:00\",\"2023-10-31T22:00\",\"2023-10-31T23:00\",\"2023-11-01T00:00\",\"2023-11-01T01:00\",\"2023-11-01T02:00\",\"2023-11-01T03:00\",\"2023-11-01T04:00\",\"2023-11-01T05:00\",\"2023-11-01T06:00\",\"2023-11-01T07:00\",\"2023-11-01T08:00\",\"2023-11-01T09:00\",\"2023-11-01T10:00\",\"2023-11-01T11:00\",\"2023-11-01T12:00\",\"2023-11-01T13:00\",\"2023-11-01T14:00\",\"2023-11-01T15:00\",\"2023-11-01T16:00\",\"2023-11-01T17:00\",\"2023-11-01T18:00\",\"2023-11-01T19:00\",\"2023-11-01T20:00\",\"2023-11-01T21:00\",\"2023-11-01T22:00\",\"2023-11-01T23:00\"],\"temperature_2m\":[8.0,7.7,6.8,7.0,7.0,6.8,7.0,7.1,7.4,8.0,9.0,10.5,13.8,15.3,15.2,15.1,14.9,13.9,12.9,12.3,11.8,11.2,11.2,10.8,10.1,9.9,9.4,9.1,8.7,8.2,7.9,7.6,7.7,8.6,9.7,10.6,11.4,11.5,11.5,11.4,11.3,11.1,11.0,10.8,10.5,10.3,10.1,10.2,10.0,9.5,9.7,9.8,10.0,10.3,10.0,9.3,8.8,9.5,10.8,12.1,13.1,13.3,13.2,13.3,12.8,11.7,10.8,10.4,10.2,9.6,8.8,8.0,7.5,7.2,7.1,7.0,6.8,6.6,6.4,6.1,5.3,6.8,9.2,11.6,14.0,16.4,17.8,17.7,16.8,15.6,14.1,12.4,11.0,10.2,9.8,9.3,8.8,8.2,7.8,7.4,7.1,6.8,6.2,5.7,5.9,7.6,10.0,12.4,14.6,16.7,18.0,18.2,17.6,16.6,15.2,13.5,12.0,11.1,10.4,9.9,9.7,9.7,9.7,11.3,11.8,12.7,14.4,16.5,18.1,19.0,19.5,19.6,19.4,18.9,18.1,17.0,15.5,14.3,13.5,12.9,12.5,12.3,12.3,12.2,11.9,11.6,11.2,10.8,10.4,10.0,9.4,8.9,8.9,9.8,11.3,12.5,13.4,14.1,14.3,13.9,13.0,12.0,11.0,9.9,9.0,8.6,8.4,8.0],\"precipitation_probability\":[0,0,0,0,0,0,6,13,19,15,10,6,8,11,13,16,20,23,16,10,3,12,20,29,29,29,29,20,12,3,2,1,0,6,13,19,32,45,58,68,77,87,90,94,97,93,88,84,76,69,61,52,44,35,28,20,13,12,11,10,15,21,26,24,21,19,17,15,13,11,8,6,5,4,3,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,4,6,6,6,6,9,13,16,15,14,13,16,20,23,30,38,45,45,45,45,45,45,45,47,50,52,51,49,48,49,51,52,45,39,32,32,32,32,32,32,32,31,30,29,26,22,19]},\"daily_units\":{\"time\":\"iso8601\",\"sunrise\":\"iso8601\",\"sunset\":\"iso8601\"},\"daily\":{\"time\":[\"2023-10-26\",\"2023-10-27\",\"2023-10-28\",\"2023-10-29\",\"2023-10-30\",\"2023-10-31\",\"2023-11-01\"],\"sunrise\":[\"2023-10-26T07:19\",\"2023-10-27T07:21\",\"2023-10-28T07:22\",\"2023-10-29T07:24\",\"2023-10-30T07:26\",\"2023-10-31T07:27\",\"2023-11-01T07:29\"],\"sunset\":[\"2023-10-26T17:28\",\"2023-10-27T17:26\",\"2023-10-28T17:24\",\"2023-10-29T17:23\",\"2023-10-30T17:21\",\"2023-10-31T17:19\",\"2023-11-01T17:17\"]}}").toString(), OpenMeteoDTO.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException("OpenMeteo workaround error :D");
            }
        }
        //workaround
        catch (Exception e) {
            throw new RuntimeException("Error when getting data from OpenMeteo");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        for (int i = 0; i < data.getDaily().get("time").size(); i++) {
            OpenMeteoDayWeather current = new OpenMeteoDayWeather(location);

            current.setDate(LocalDate.parse(data.getDaily().get("time").get(i)));
            current.setSunrise(LocalTime.parse(data.getDaily().get("sunrise").get(i), formatter));
            current.setSunset(LocalTime.parse(data.getDaily().get("sunset").get(i), formatter));

            for (int j = 0; j < data.getHourly().get("time").size(); j++) {
                double maxPrecipitationChance = 0;

                if (current.getDate().equals(LocalDate.parse(data.getHourly().get("time").get(j), formatter))) {
                    current.addTime(LocalTime.parse(data.getHourly().get("time").get(j), formatter));
                    current.addTemp(Double.parseDouble(data.getHourly().get("temperature_2m").get(j)));

                    if (Double.parseDouble(data.getHourly().get("temperature_2m").get(j)) > maxPrecipitationChance)
                        maxPrecipitationChance = Double.parseDouble(data.getHourly().get("temperature_2m").get(j));

                }

                current.setPrecipitationChance(maxPrecipitationChance);
            }

            result.add(current);
        }

        System.out.println("OpenMeteo done... " + result);

        return result;
    }
}