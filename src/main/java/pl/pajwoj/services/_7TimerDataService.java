package pl.pajwoj.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import pl.pajwoj.dtos._7TimerDTO;
import pl.pajwoj.models.Location;
import pl.pajwoj.models._7TimerDayWeather;

import javax.net.ssl.SSLHandshakeException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class _7TimerDataService {
    public static ArrayList<_7TimerDayWeather> get(Location location) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<_7TimerDayWeather> result = new ArrayList<>();

        String link = "http://www.7timer.info/bin/api.pl?lon=" +
                location.getLon() +
                "&lat=" +
                location.getLat() +
                "&product=civil&output=json&unit=metric";

        Gson gson = new Gson();
        _7TimerDTO data;

        System.out.println("7Timer raw JSON: " + link);

        try {
            data = gson.fromJson((objectMapper.readTree(new URL(link)).toString()), _7TimerDTO.class);
            System.out.println("Got 7Timer data, parsing...");
        } catch (SSLHandshakeException e) {
            try {
                System.out.println("SSLHandshakeException for 7Timer occurred, trying workaround...");
                data = gson.fromJson(objectMapper.readTree("{ \"product\" : \"civil\" , \"init\" : \"2023102612\" , \"dataseries\" : [ { \"timepoint\" : 3, \"cloudcover\" : 5, \"lifted_index\" : 2, \"prec_type\" : \"none\", \"prec_amount\" : 1, \"temp2m\" : 13, \"rh2m\" : \"63%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"pcloudyday\" }, { \"timepoint\" : 6, \"cloudcover\" : 5, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"74%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"pcloudynight\" }, { \"timepoint\" : 9, \"cloudcover\" : 7, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"76%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"mcloudynight\" }, { \"timepoint\" : 12, \"cloudcover\" : 8, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"81%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 15, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"84%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 18, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 21, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 12, \"rh2m\" : \"71%\", \"wind10m\" : { \"direction\" : \"E\", \"speed\" : 2 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 24, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 1, \"temp2m\" : 11, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"NE\", \"speed\" : 2 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 27, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 2, \"temp2m\" : 10, \"rh2m\" : \"95%\", \"wind10m\" : { \"direction\" : \"N\", \"speed\" : 3 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 30, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"92%\", \"wind10m\" : { \"direction\" : \"NW\", \"speed\" : 3 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 33, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"95%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 36, \"cloudcover\" : 9, \"lifted_index\" : 2, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 4 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 39, \"cloudcover\" : 4, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 8, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"pcloudynight\" }, { \"timepoint\" : 42, \"cloudcover\" : 6, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"79%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"mcloudyday\" }, { \"timepoint\" : 45, \"cloudcover\" : 7, \"lifted_index\" : 2, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"65%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"mcloudyday\" }, { \"timepoint\" : 48, \"cloudcover\" : 7, \"lifted_index\" : 2, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 13, \"rh2m\" : \"64%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"mcloudyday\" }, { \"timepoint\" : 51, \"cloudcover\" : 7, \"lifted_index\" : 2, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"69%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"oshowerday\" }, { \"timepoint\" : 54, \"cloudcover\" : 6, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 8, \"rh2m\" : \"78%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"oshowernight\" }, { \"timepoint\" : 57, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"75%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 60, \"cloudcover\" : 7, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"75%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"mcloudynight\" }, { \"timepoint\" : 63, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"78%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 66, \"cloudcover\" : 8, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"72%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 69, \"cloudcover\" : 7, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 13, \"rh2m\" : \"60%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"mcloudyday\" }, { \"timepoint\" : 72, \"cloudcover\" : 5, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 16, \"rh2m\" : \"53%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"pcloudyday\" }, { \"timepoint\" : 75, \"cloudcover\" : 5, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 13, \"rh2m\" : \"66%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"pcloudyday\" }, { \"timepoint\" : 78, \"cloudcover\" : 7, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"74%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"mcloudynight\" }, { \"timepoint\" : 81, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"79%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 84, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 87, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"79%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 90, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 93, \"cloudcover\" : 8, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 16, \"rh2m\" : \"62%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 96, \"cloudcover\" : 9, \"lifted_index\" : 2, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 17, \"rh2m\" : \"56%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 3 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 99, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 14, \"rh2m\" : \"68%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 102, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 13, \"rh2m\" : \"74%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 105, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 13, \"rh2m\" : \"68%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 108, \"cloudcover\" : 8, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 12, \"rh2m\" : \"77%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 111, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"84%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 114, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"89%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 117, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 15, \"rh2m\" : \"73%\", \"wind10m\" : { \"direction\" : \"E\", \"speed\" : 2 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 120, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 15, \"rh2m\" : \"75%\", \"wind10m\" : { \"direction\" : \"E\", \"speed\" : 3 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 123, \"cloudcover\" : 9, \"lifted_index\" : 2, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 14, \"rh2m\" : \"81%\", \"wind10m\" : { \"direction\" : \"E\", \"speed\" : 2 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 126, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 1, \"temp2m\" : 12, \"rh2m\" : \"92%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 2 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 129, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"90%\", \"wind10m\" : { \"direction\" : \"NW\", \"speed\" : 2 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 132, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"91%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 2 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 135, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"91%\", \"wind10m\" : { \"direction\" : \"NW\", \"speed\" : 3 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 138, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"86%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 141, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"75%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 144, \"cloudcover\" : 7, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 13, \"rh2m\" : \"70%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"oshowerday\" }, { \"timepoint\" : 147, \"cloudcover\" : 4, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"pcloudyday\" }, { \"timepoint\" : 150, \"cloudcover\" : 3, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 8, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"pcloudynight\" }, { \"timepoint\" : 153, \"cloudcover\" : 1, \"lifted_index\" : 15, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"80%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"clearnight\" }, { \"timepoint\" : 156, \"cloudcover\" : 1, \"lifted_index\" : 15, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 6, \"rh2m\" : \"82%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"clearnight\" }, { \"timepoint\" : 159, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"78%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudynight\" }, { \"timepoint\" : 162, \"cloudcover\" : 9, \"lifted_index\" : 15, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"75%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 165, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"55%\", \"wind10m\" : { \"direction\" : \"S\", \"speed\" : 2 }, \"weather\" : \"cloudyday\" }, { \"timepoint\" : 168, \"cloudcover\" : 6, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 15, \"rh2m\" : \"50%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 2 }, \"weather\" : \"mcloudyday\" }, { \"timepoint\" : 171, \"cloudcover\" : 2, \"lifted_index\" : 6, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"69%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 3 }, \"weather\" : \"clearday\" }, { \"timepoint\" : 174, \"cloudcover\" : 5, \"lifted_index\" : 10, \"prec_type\" : \"none\", \"prec_amount\" : 0, \"temp2m\" : 9, \"rh2m\" : \"79%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 3 }, \"weather\" : \"pcloudynight\" }, { \"timepoint\" : 177, \"cloudcover\" : 9, \"lifted_index\" : 10, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 11, \"rh2m\" : \"70%\", \"wind10m\" : { \"direction\" : \"SE\", \"speed\" : 3 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 180, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 10, \"rh2m\" : \"77%\", \"wind10m\" : { \"direction\" : \"SW\", \"speed\" : 2 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 183, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 8, \"rh2m\" : \"88%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"lightrainnight\" }, { \"timepoint\" : 186, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 7, \"rh2m\" : \"88%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 189, \"cloudcover\" : 9, \"lifted_index\" : 6, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 6, \"rh2m\" : \"89%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"lightrainday\" }, { \"timepoint\" : 192, \"cloudcover\" : 9, \"lifted_index\" : 2, \"prec_type\" : \"rain\", \"prec_amount\" : 0, \"temp2m\" : 6, \"rh2m\" : \"86%\", \"wind10m\" : { \"direction\" : \"W\", \"speed\" : 3 }, \"weather\" : \"lightrainday\" } ] } ").toString(), _7TimerDTO.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException("7Timer workaround error :D");
            }
        }
        //workaround
        catch (Exception e) {
            throw new RuntimeException("Error when getting data from 7Timer");
        }

        int iterator = 0;
        result.add(new _7TimerDayWeather(location));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");

        LocalDateTime initDateTime = LocalDateTime.parse(data.getInit(), formatter);
        result.get(0).setDate(initDateTime.toLocalDate());

        for(int i=0; i<data.getDataseries().size(); i++) {
            initDateTime = initDateTime.plusHours(3);

            if(initDateTime.toLocalDate().isEqual(result.get(iterator).getDate())) {
                result.get(iterator).addTime(initDateTime.toLocalTime());
                result.get(iterator).addTemp((Double) data.getDataseries().get(i).get("temp2m"));
                result.get(iterator).addPrecipitation((String) data.getDataseries().get(i).get("prec_type"));
            }

            else {
                iterator++;
                _7TimerDataService.newWeatherObjectSetup(result, location, initDateTime, data, i);
            }
        }

        System.out.println("7Timer done... " + result);

        return result;
    }

    private static void newWeatherObjectSetup(ArrayList<_7TimerDayWeather> result, Location location, LocalDateTime firstDateTime, _7TimerDTO data, int i) {
        _7TimerDayWeather newWeather = new _7TimerDayWeather(location);
        result.add(newWeather);

        newWeather.setDate(firstDateTime.toLocalDate());
        newWeather.addTime(firstDateTime.toLocalTime());
        newWeather.addTemp((Double) data.getDataseries().get(i).get("temp2m"));
        newWeather.addPrecipitation((String) data.getDataseries().get(i).get("prec_type"));
    }
}