package pl.pajwoj.services;

import org.springframework.stereotype.Service;
import pl.pajwoj.Utilities;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AverageService {
    public static void get(Location location, Map<String, ArrayList<DayWeather>> data) {
        ArrayList<DayWeather> result = resultInit(location);
        Map<String, Integer> valid = checkDataValidity(data);
        
        System.out.println("Calculating averages of data...");

        data.forEach((key, value) -> {
            if (value != null) {
                for (int i = 0; i < 7; i++) {

                    if (Utilities.round(value.get(i).getPrecipitationChance(), 1) <= 100 && Utilities.round(value.get(i).getPrecipitationChance(), 1) >= 0) {
                        if (result.get(i).getPrecipitationChance() == -1.0)
                            result.get(i).precipitationChance(0.0);

                        result.get(i).precipitationChance(Utilities.round(result.get(i).getPrecipitationChance() + (value.get(i).getPrecipitationChance() / valid.get("precipitationChance")), 1));
                    }

                    if (value.get(i).getSunset() != null && value.get(i).getSunrise() != null) {
                        if (result.get(i).getSunrise() == null)
                            result.get(i).sunrise(LocalTime.ofNanoOfDay(0));

                        if (result.get(i).getSunset() == null)
                            result.get(i).sunset(LocalTime.ofNanoOfDay(0));

                        result.get(i).sunset(LocalTime.ofNanoOfDay(result.get(i).getSunset().toNanoOfDay() + (value.get(i).getSunset().toNanoOfDay() / valid.get("sunriseSunset"))).truncatedTo(ChronoUnit.SECONDS));
                        result.get(i).sunrise(LocalTime.ofNanoOfDay(result.get(i).getSunrise().toNanoOfDay() + (value.get(i).getSunrise().toNanoOfDay() / valid.get("sunriseSunset"))).truncatedTo(ChronoUnit.SECONDS));
                    }

                    for (int j = 0; j < value.get(i).getTimes().size(); j++) { //j = current index of data (may or may not be equal to current index of result)
                        for (int k = 0; k < result.get(i).getTimes().size(); k++) { //k - current index of result
                            if (result.get(i).getTimes().get(k).equals(value.get(i).getTimes().get(j))) {
                                if (result.get(i).getTemps().size() < result.get(i).getTimes().size()) { //first dataset, create new temp and precipitation
                                    if (i == 0) { //today, skip 7timer
                                        if (k < valid.get("7TimerFirstDaySkip"))  //divide by temps - 1 (no 7timer data)
                                            result.get(i).newTemp(Utilities.round(value.get(i).getTemps().get(j) / (valid.get("temps") - 1), 1));

                                        else //divide by temps (all data)
                                            result.get(i).newTemp(Utilities.round(value.get(i).getTemps().get(j) / valid.get("temps"), 1));
                                    }

                                    else //not today, all data, divide by all temps
                                        result.get(i).newTemp(Utilities.round(value.get(i).getTemps().get(j) / valid.get("temps"), 1));

                                    try {
                                        Double.parseDouble(value.get(i).getPrecipitation().get(j)); //to make sure no new entry gets added
                                        result.get(i).newPrecipitation(Utilities.round(Double.parseDouble(value.get(i).getPrecipitation().get(j)) / valid.get("precipitation"), 1).toString());
                                    } catch (NumberFormatException ignored) { }
                                }

                                else { //one dataset imported, just add, no need to create new temp and precipitation
                                    if (i == 0) { //today, skip 7timer
                                        if (k < valid.get("7TimerFirstDaySkip")) //divide by temps - 1 (no 7timer data)
                                            result.get(i).getTemps().set(k, Utilities.round((value.get(i).getTemps().get(j) / (valid.get("temps") - 1)) + result.get(i).getTemps().get(k), 1));

                                        else //divide by temps (all data)
                                            result.get(i).getTemps().set(k, Utilities.round((value.get(i).getTemps().get(j) / valid.get("temps")) + result.get(i).getTemps().get(k), 1));
                                    }

                                    else //not today, all data, divide by all temps
                                        result.get(i).getTemps().set(k, Utilities.round((value.get(i).getTemps().get(j) / valid.get("temps")) + result.get(i).getTemps().get(k), 1));

                                    try {
                                        Double.parseDouble(value.get(i).getPrecipitation().get(j)); //to make sure no new entry gets added
                                        result.get(i).getPrecipitation().set(k, Utilities.round((Double.parseDouble(value.get(i).getPrecipitation().get(j)) / valid.get("precipitation")) + Double.parseDouble(result.get(i).getPrecipitation().get(k)), 1).toString());
                                    } catch (NumberFormatException ignored) { }
                                }
                            }
                        }
                    }
                }
            }
        });

        System.out.println("Averages done... " + result);

        data.put("Average", result);
    }

    private static ArrayList<DayWeather> resultInit(Location location) {
        ArrayList<DayWeather> result = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            DayWeather current = new DayWeather(location);
            current.date(LocalDate.now().plusDays(i));

            for (int j = 0; j < 24; j += 3)
                if (current.getDate().isEqual(LocalDate.now()) && j == 0) continue;
                else current.newTime(LocalTime.of(j, 0));

            result.add(current);
        }

        return result;
    }

    private static Map<String, Integer> checkDataValidity(Map<String, ArrayList<DayWeather>> data) {
        Map<String, Integer> result = new HashMap<>();
        result.put("temps", 0);
        result.put("precipitation", 0);
        result.put("precipitationChance", 0);
        result.put("sunriseSunset", 0);
        result.put("7TimerFirstDaySkip", 0);

        data.forEach((key, value) -> {
            if (value != null) {
                result.merge("temps", 1, Integer::sum);

                if (key.equals("7Timer")) result.replace("7TimerFirstDaySkip", 7 - value.get(0).getTimes().size());


                try {
                    Double.parseDouble(value.get(0).getPrecipitation().get(0));
                    result.merge("precipitation", 1, Integer::sum);
                } catch (NumberFormatException ignored) {
                }

                if (Utilities.round(value.get(0).getPrecipitationChance(), 1) <= 100 && Utilities.round(value.get(0).getPrecipitationChance(), 1) >= 0)
                    result.merge("precipitationChance", 1, Integer::sum);
                if (value.get(0).getSunset() != null && value.get(0).getSunrise() != null)
                    result.merge("sunriseSunset", 1, Integer::sum);
            }
        });
        return result;
    }
}
