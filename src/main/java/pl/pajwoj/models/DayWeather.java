package pl.pajwoj.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DayWeather {
    private final Location location;
    private final List<Double> temps = new ArrayList<>();
    private final List<LocalTime> times = new ArrayList<>();
    private final List<String> precipitation = new ArrayList<>();
    private Double precipitationChance = -1.0;
    private LocalDate date;
    private LocalTime sunrise;
    private LocalTime sunset;

    public DayWeather(Location location) {
        this.location = location;
    }

    public DayWeather newTemp(Double temp) {
        this.temps.add(temp);
        return this;
    }

    public DayWeather newTime(LocalTime time) {
        this.times.add(time);
        return this;
    }

    public DayWeather newPrecipitation(String precipitation) {
        this.precipitation.add(precipitation);
        return this;
    }

    public DayWeather date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DayWeather sunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public DayWeather sunset(LocalTime sunset) {
        this.sunset = sunset;
        return this;
    }

    public DayWeather precipitationChance(Double precipitationChance) {
        this.precipitationChance = precipitationChance;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getPrecipitationChance() {
        return precipitationChance;
    }

    public List<String> getPrecipitation() {
        return precipitation;
    }

    @Override
    public String toString() {
        return "DayWeather{" +
                "location=" + location +
                ", temps=" + temps +
                ", times=" + times +
                ", precipitation=" + precipitation +
                ", precipitationChance=" + precipitationChance +
                ", date=" + date +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}
