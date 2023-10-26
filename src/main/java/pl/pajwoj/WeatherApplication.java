package pl.pajwoj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pajwoj.models.OpenMeteoDayWeather;
import pl.pajwoj.models.Location;
import pl.pajwoj.services.OpenMeteoDataService;

import java.util.ArrayList;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		Location k = new Location("krakow");
		ArrayList<OpenMeteoDayWeather> test = OpenMeteoDataService.get(k);
	}
}
