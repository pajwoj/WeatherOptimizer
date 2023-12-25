package pl.pajwoj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pajwoj.config.TrayIconConfig;
import pl.pajwoj.models.DayWeather;
import pl.pajwoj.models.Location;
import pl.pajwoj.services.ECMWFDataService;
import pl.pajwoj.services.OpenMeteoDataService;
import pl.pajwoj.services.ScrapperService;
import pl.pajwoj.services._7TimerDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		Location k = new Location("polko wielkopolskie");

		Map<String, ArrayList<DayWeather>> forecastMap = new HashMap<>();

		forecastMap.put("OpenMeteo", OpenMeteoDataService.get(k));
		forecastMap.put("7Timer", _7TimerDataService.get(k));
		forecastMap.put("ECMWF", ECMWFDataService.get(k));
		forecastMap.put("Pogoda Dziennik", ScrapperService.get(k));

		TrayIconConfig.init();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
