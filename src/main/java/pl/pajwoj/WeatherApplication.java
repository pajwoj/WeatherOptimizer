package pl.pajwoj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pajwoj.models.OpenMeteoDayWeather;
import pl.pajwoj.models.Location;
import pl.pajwoj.models._7TimerDayWeather;
import pl.pajwoj.services.OpenMeteoDataService;
import pl.pajwoj.services._7TimerDataService;

import java.util.ArrayList;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		Location k = new Location("krakow");
		ArrayList<OpenMeteoDayWeather> test = OpenMeteoDataService.get(k);
		ArrayList<_7TimerDayWeather> test2 = _7TimerDataService.get(k);

		System.out.println(test2);
	}
}
