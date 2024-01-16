package pl.pajwoj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pajwoj.config.TrayIconConfig;

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
