package pl.pajwoj.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class _7TimerDayWeather {
    private final Location location;
    private List<Double> temps = new ArrayList<>();
    private List<LocalTime> times = new ArrayList<>();
    private List<String> precipitation = new ArrayList<>();
    private LocalDate date;

    public _7TimerDayWeather(Location location) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        return "_7TimerDayWeather{" +
                "location=" + location +
                ", temps=" + temps +
                ", times=" + times +
                ", precipitation=" + precipitation +
                ", date=" + date +
                '}';
    }
}
