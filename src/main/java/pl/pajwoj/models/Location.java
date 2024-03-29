package pl.pajwoj.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.pajwoj.Utilities;

import java.net.URL;

public class Location {
    private final String lat;
    private final String lon;
    private final String locationString;

    public Location(String input) {
        ObjectMapper objectMapper = new ObjectMapper();

        input = Utilities.removeDiacritics(input);

        String link = "https://nominatim.openstreetmap.org/search.php?q="
                + input.replace(' ', '+')
                + "&format=jsonv2&accept-language=pl";

        try {
            System.out.println("Getting data for " + link + "...");
            JsonNode result = objectMapper.readTree(new URL(link));

            lat = (result.get(0).get("lat").asText());
            lon = (result.get(0).get("lon").asText());
            locationString = (result.get(0).get("display_name").asText());
        } catch (Exception e) {
            throw new RuntimeException("Error when getting location data (wrong location?)");
        }
    }

    public static void checkValidity(String input) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        input = Utilities.removeDiacritics(input);

        String link = "https://nominatim.openstreetmap.org/search.php?q="
                + input.replace(' ', '+')
                + "&format=jsonv2&accept-language=pl";

        try {
            if (objectMapper.readTree(new URL(link)).isEmpty())
                throw new RuntimeException("Error when getting location data (wrong location?)");
        } catch (Exception e) {
            throw new RuntimeException("Error when getting location data (wrong location?)");
        }
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getLocationString() {
        return locationString;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", locationString='" + locationString + '\'' +
                '}';
    }
}
