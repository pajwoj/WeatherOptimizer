package pl.pajwoj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pajwoj.models.*;
import pl.pajwoj.services.ECMWFDataService;
import pl.pajwoj.services.ScrapperService;
import pl.pajwoj.services.OpenMeteoDataService;
import pl.pajwoj.services._7TimerDataService;

import java.util.ArrayList;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		Location k = new Location("pruszcz gdanski");
		ArrayList<DayWeather> test = OpenMeteoDataService.get(k);
		ArrayList<DayWeather> test2 = _7TimerDataService.get(k);
		ArrayList<DayWeather> test3 = ECMWFDataService.get(k);
		ArrayList<DayWeather> test4 = ScrapperService.get(k);
	}
}
