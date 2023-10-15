package pl.pajwoj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pajwoj.models.Location;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		Location k = new Location("la paz");
		System.out.printf(k.getLat() + " " + k.getLon() + " " + k.getLocationString());
	}

}
