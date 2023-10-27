package pl.pajwoj.dtos;

import java.util.ArrayList;
import java.util.Map;

public class ECMWF_DTO {
    private Map<String, ArrayList<Object>> hourly;

    public Map<String, ArrayList<Object>> getHourly() {
        return hourly;
    }
}
