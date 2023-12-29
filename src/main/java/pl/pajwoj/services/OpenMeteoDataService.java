package pl.pajwoj.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import pl.pajwoj.dtos.OpenMeteoDTO;
import pl.pajwoj.exceptions.OpenMeteoDataException;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class OpenMeteoDataService {
    public static ArrayList<DayWeather> get(Location location) throws OpenMeteoDataException {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<DayWeather> result = new ArrayList<>();

        String link =
                "https://api.open-meteo.com/v1/forecast?latitude=" +
                        location.getLat() +
                        "&longitude=" +
                        location.getLon() +
                        "&hourly=temperature_2m,precipitation_probability,precipitation&daily=sunrise,sunset&timezone=Europe%2FBerlin";

        Gson gson = new Gson();
        OpenMeteoDTO data;

        System.out.println("OpenMeteo raw JSON: " + link);

        try {
            data = gson.fromJson((objectMapper.readTree(new URL(link)).toString()), OpenMeteoDTO.class);
            System.out.println("Got OpenMeteo data, parsing...");
        } catch (Exception e) {
            throw new OpenMeteoDataException("Error when getting data from OpenMeteo");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        for (int i = 0; i < data.getDaily().get("time").size(); i++) {
            DayWeather current = new DayWeather(location);

            current
                    .date(LocalDate.parse(data.getDaily().get("time").get(i)))
                    .sunrise(LocalTime.parse(data.getDaily().get("sunrise").get(i), formatter))
                    .sunset(LocalTime.parse(data.getDaily().get("sunset").get(i), formatter));

            result.add(current);
        }

        for (int i = 0; i < data.getHourly().get("time").size(); i++) {
            for (DayWeather current : result) {
                if (current.getDate().equals(LocalDate.parse(data.getHourly().get("time").get(i), formatter))) {

                    current
                            .newTime(LocalTime.parse(data.getHourly().get("time").get(i), formatter))
                            .newTemp(Double.parseDouble(data.getHourly().get("temperature_2m").get(i)))
                            .newPrecipitation(data.getHourly().get("precipitation").get(i));

                    if (Double.parseDouble(data.getHourly().get("precipitation_probability").get(i)) > current.getPrecipitationChance())
                        current.precipitationChance(Double.parseDouble(data.getHourly().get("precipitation_probability").get(i)));
                }
            }
        }

        System.out.println("OpenMeteo done... " + result);

        return result;
    }
}