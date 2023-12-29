package pl.pajwoj.services;

import org.springframework.stereotype.Service;
import pl.pajwoj.exceptions.ECMWFDataException;
import pl.pajwoj.exceptions.OpenMeteoDataException;
import pl.pajwoj.exceptions.ScrapperDataException;
import pl.pajwoj.exceptions._7TimerDataException;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataGrabberService {
    public Map<String, ArrayList<DayWeather>> getData(String locationInput) {
        try {
            Location.checkValidity(locationInput);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }

        Location location = new Location(locationInput);
        System.out.println("Grabbing data for " + location.getLocationString() + "...");
        Map<String, ArrayList<DayWeather>> result = new HashMap<>();

        try {
            result.put("OpenMeteo", OpenMeteoDataService.get(location));
            result.put("7Timer", _7TimerDataService.get(location));
            result.put("ECMWF", ECMWFDataService.get(location));
            result.put("Pogoda Dziennik", ScrapperService.get(location));
        } catch (OpenMeteoDataException e) {
            System.out.println(e.getMessage());
            result.put("OpenMeteo", null);
        } catch (_7TimerDataException e) {
            System.out.println(e.getMessage());
            result.put("7Timer", null);
        } catch (ECMWFDataException e) {
            System.out.println(e.getMessage());
            result.put("ECMWF", null);
        } catch (ScrapperDataException e) {
            System.out.println(e.getMessage());
            result.put("Pogoda Dziennik", null);
        }

        AverageService.get(location, result);

        return result;
    }
}
