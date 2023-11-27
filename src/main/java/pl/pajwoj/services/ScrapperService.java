package pl.pajwoj.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.pajwoj.dtos.ScrapperDTO;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class ScrapperService {
    public static ArrayList<DayWeather> get(Location location) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<DayWeather> result = new ArrayList<>();

        Document doc;
        Gson gson = new Gson();
        ScrapperDTO data;

        try {
            String link =
                    "https://pogoda.dziennik.pl/pogoda-godzinowa/polska/" +
                            ScrapperService.getCityFromLocation(location);

            System.out.println("Pogoda Dziennik link: " + link);

            doc = Jsoup.connect(link).get();
            System.out.println("Got Pogoda Dziennik HTML file, getting JSON...");

            String rawDataJSON = "";

            for (Element e :
                    doc.getElementsByTag("script")) {
                if (e.html().contains("window.weatherLongTerm")) rawDataJSON = e.html();
            }

            data = gson.fromJson(objectMapper.readTree(rawDataJSON.split(" = ")[1]).toString(), ScrapperDTO.class);
            System.out.println("Got Pogoda Dziennik data, parsing...");
            data.parseForecast();
        } catch (Exception e) {
            System.out.println("Error when getting data from Pogoda Dziennik");
            System.out.println(e.getClass().getCanonicalName());
            throw new RuntimeException("Error when getting data from Pogoda Dziennik");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        for (Map<String, Object> current :
                data.getSunriseSunset()) {
            DayWeather currentWeather = new DayWeather(location);

            currentWeather
                    .date(LocalDate.parse(current.get("sunrise").toString(), formatter))
                    .sunrise(LocalTime.parse(current.get("sunrise").toString(), formatter))
                    .sunset(LocalTime.parse(current.get("sunset").toString(), formatter));

            result.add(currentWeather);
        }

        for (Map<String, Object> current :
                data.getHourly()) {

            for (DayWeather currentWeather :
                    result) {
                if (LocalDate.parse(current.get("dateTime").toString(), formatter).isEqual(currentWeather.getDate())) {
                    currentWeather
                            .newTime(LocalTime.parse(current.get("dateTime").toString(), formatter))
                            .newTemp(Double.parseDouble(current.get("temperature").toString()))
                            .newPrecipitation(
                                    (Double.parseDouble(current.get("rain").toString())
                                            + Double.parseDouble(current.get("snow").toString()))
                                            + ""
                            );
                }
            }
        }

        calculatePrecipitationChance(result);

        System.out.println("Pogoda Dziennik done... " + result);

        return result;
    }

    private static String getCityFromLocation(Location location) {
        String result = location.getLocationString();
        result = result.split(",")[0];

        result = result.replaceAll("[ąĄ]", "a");
        result = result.replaceAll("[ćĆ]", "c");
        result = result.replaceAll("[ęĘ]", "e");
        result = result.replaceAll("[łŁ]", "l");
        result = result.replaceAll("[óÓ]", "o");
        result = result.replaceAll("[śŚ]", "s");
        result = result.replaceAll("[ńŃ]", "n");
        result = result.replaceAll("[żŻźŹ]", "z");
        result = result.replaceAll("[ ]", "-");

        return result.toLowerCase();
    }

    private static void calculatePrecipitationChance(ArrayList<DayWeather> result) {
        for (DayWeather current:
                result) {
            for (String currentPrecipitation:
                    current.getPrecipitation()) {
                if(Double.parseDouble(currentPrecipitation) > 0) {
                    current.precipitationChance(100.0);
                    break;
                }
                current.precipitationChance(0.0);
            }
        }
    }
}
