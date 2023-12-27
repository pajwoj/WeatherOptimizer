package pl.pajwoj.services;

import pl.pajwoj.Utilities;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AverageService {
    public static void get(Location location, Map<String, ArrayList<DayWeather>> data) {
        ArrayList<DayWeather> result = resultInit(location);
        Map<String, Integer> valid = checkDataValidity(data);

        System.out.println(valid);

        data.forEach((key, value) -> {
            if (value != null) {

            }
        });

        data.put("Average", result);
    }

    private static ArrayList<DayWeather> resultInit(Location location) {
        ArrayList<DayWeather> result = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            DayWeather current = new DayWeather(location);
            current.date(LocalDate.now().plusDays(i))
                    .sunrise(null)
                    .sunset(null);

            for (int j = 0; j < 24; j+=3) {
                current.newTime(LocalTime.of(j, 0))
                        .newTemp(-1.0)
                        .newPrecipitation(null);
            }

            result.add(current);
        }

        return result;
    }

    private static Map<String, Integer> checkDataValidity(Map<String, ArrayList<DayWeather>> data) {
        Map<String, Integer> result = new HashMap<>();
        result.put("temps", 0);
        result.put("times", 0);
        result.put("precipitation", 0);
        result.put("precipitationChance", 0);
        result.put("sunriseSunset", 0);

        data.forEach((key, value) -> {
            if (value != null) {
                if(value.get(0).getTemps().get(0) != null) result.merge("temps", 1, Integer::sum);
                if(value.get(0).getTimes().get(0) != null) result.merge("times", 1, Integer::sum);

                try {
                    Double.parseDouble(value.get(0).getPrecipitation().get(0));
                    result.merge("precipitation", 1, Integer::sum);
                } catch (NumberFormatException ignored) { }

                if(Utilities.round(value.get(0).getPrecipitationChance(), 1) <= 100 && Utilities.round(value.get(0).getPrecipitationChance(), 1) >= 0) result.merge("precipitationChance", 1, Integer::sum);
                if(value.get(0).getSunset() != null) result.merge("sunriseSunset", 1, Integer::sum);
            }
        });

        return result;
    }
}
