package pl.pajwoj.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.services.DataGrabberService;

import java.util.ArrayList;
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
    public ResponseEntity<Map<String, ArrayList<DayWeather>>> get(@RequestParam String location) {
        Map<String, ArrayList<DayWeather>> result = service.getData(location);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        else
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
