package pl.pajwoj.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.SSLHandshakeException;
import java.net.URL;

public class Location {
    private String input;
    private String lat;
    private String lon;
    private String locationString;

    public Location(String input) {
        ObjectMapper objectMapper = new ObjectMapper();
        String link = "https://nominatim.openstreetmap.org/search.php?q="
                + input.replace(' ', '+')
                + "&format=jsonv2&accept-language=pl";

        try {
            System.out.println("Getting data from " + link + "...");
            JsonNode result = objectMapper.readTree(new URL(link));

            lat = (result.get(0).get("lat").asText());
            lon = (result.get(0).get("lon").asText());
            locationString = (result.get(0).get("display_name").asText());
        }
        //workaround for laptop:)
        catch (SSLHandshakeException e) {
            try {
                JsonNode result = objectMapper.readTree("[{\"place_id\":192943614,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"relation\",\"osm_id\":2768922,\"lat\":\"50.0469432\",\"lon\":\"19.997153435836697\",\"category\":\"boundary\",\"type\":\"administrative\",\"place_rank\":16,\"importance\":0.6936604637257605,\"addresstype\":\"administrative\",\"name\":\"Kraków\",\"display_name\":\"Kraków, województwo małopolskie, Polska\",\"boundingbox\":[\"49.9676668\",\"50.1261338\",\"19.7922355\",\"20.2173455\"]},{\"place_id\":191790546,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"relation\",\"osm_id\":449696,\"lat\":\"50.0619474\",\"lon\":\"19.9368564\",\"category\":\"boundary\",\"type\":\"administrative\",\"place_rank\":12,\"importance\":0.6936604637257605,\"addresstype\":\"city\",\"name\":\"Kraków\",\"display_name\":\"Kraków, województwo małopolskie, Polska\",\"boundingbox\":[\"49.9676668\",\"50.1261338\",\"19.7922355\",\"20.2173455\"]}]");

                lat = (result.get(0).get("lat").asText());
                lon = (result.get(0).get("lon").asText());
                locationString = (result.get(0).get("display_name").asText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        //end of workaround for laptop:)
        catch (Exception e) {
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

    public String getInput() {
        return input;
    }
}
