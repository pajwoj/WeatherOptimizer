package pl.pajwoj.dtos;

import java.util.ArrayList;
import java.util.Map;

public class OpenMeteoDTO {
    private Map<String, ArrayList<String>> hourly;
    private Map<String, ArrayList<String>> daily;

    public Map<String, ArrayList<String>> getHourly() {
        return hourly;
    }

    public Map<String, ArrayList<String>> getDaily() {
        return daily;
    }
}
