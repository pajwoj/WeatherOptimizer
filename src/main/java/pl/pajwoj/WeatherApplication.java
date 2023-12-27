package pl.pajwoj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pajwoj.config.TrayIconConfig;
import pl.pajwoj.exceptions.ECMWFDataException;
import pl.pajwoj.exceptions.OpenMeteoDataException;
import pl.pajwoj.exceptions.ScrapperDataException;
import pl.pajwoj.exceptions._7TimerDataException;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;
import pl.pajwoj.services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WeatherApplication {

    public static void main(String[] args) {

        Location k = new Location("best");

        System.out.println(k);

        Map<String, ArrayList<DayWeather>> forecastMap = new HashMap<>();

        try {
            forecastMap.put("OpenMeteo", OpenMeteoDataService.get(k));
            forecastMap.put("7Timer", _7TimerDataService.get(k));
            forecastMap.put("ECMWF", ECMWFDataService.get(k));
            forecastMap.put("Pogoda Dziennik", ScrapperService.get(k));
        } catch (OpenMeteoDataException e) {
            System.out.println(e.getMessage());
            forecastMap.put("OpenMeteo", null);
        } catch (_7TimerDataException e) {
            System.out.println(e.getMessage());
            forecastMap.put("7Timer", null);
        } catch (ECMWFDataException e) {
            System.out.println(e.getMessage());
            forecastMap.put("ECMWF", null);
        } catch (ScrapperDataException e) {
            System.out.println(e.getMessage());
            forecastMap.put("Pogoda Dziennik", null);
        }

        AverageService.get(k, forecastMap);
        TrayIconConfig.init();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
