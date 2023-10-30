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

    public Location getLocation() {
        return location;
    }

    public List<Double> getTemps() {
        return temps;
    }

    public List<LocalTime> getTimes() {
        return times;
    }

    public Double getPrecipitationChance() {
        return precipitationChance;
    }

    public void setPrecipitationChance(Double precipitationChance) {
        this.precipitationChance = precipitationChance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public void addTime(LocalTime time) {
        this.times.add(time);
    }

    public void addTemp(Double temp) {
        this.temps.add(temp);
    }
    public void addPrecipitation(String precipitation) {
        this.precipitation.add(precipitation);
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
