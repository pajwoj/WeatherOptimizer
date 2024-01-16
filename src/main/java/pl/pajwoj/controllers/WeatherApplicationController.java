package pl.pajwoj.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;
import pl.pajwoj.services.DataGrabberService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WeatherApplicationController {

    private final DataGrabberService service;

    public WeatherApplicationController(DataGrabberService service) {
        this.service = service;
    }

    @CrossOrigin
    @GetMapping(
            value = "api/results",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, ArrayList<DayWeather>>> getFull(@RequestParam String location) {
        Map<String, ArrayList<DayWeather>> result = service.getData(location);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        else
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(
            value = "api/quick-results",
            produces = "application/json"
    )
    public ResponseEntity<ArrayList<DayWeather>> getQuick(@RequestParam String location) {
        ArrayList<DayWeather> result = service.getQuickData(location);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        else
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(
            value = "api/geo-data",
            produces = "application/json"
    )
    public ResponseEntity<Map<String, String>> getGeoData(String location) {
        try {
            Location.checkValidity(location);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Location l = new Location(location);
        Map<String, String> result = new HashMap<>();

        result.put("latitude", l.getLat());
        result.put("longitude", l.getLon());
        result.put("name", l.getLocationString());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
