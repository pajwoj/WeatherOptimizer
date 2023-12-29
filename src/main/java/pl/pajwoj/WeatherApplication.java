package pl.pajwoj;

import org.springframework.boot.SpringApplication;
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

        TrayIconConfig.init();
        SpringApplication.run(WeatherApplication.class, args);

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
