package com.ssafy.plant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.plant.config.mqtt.MqttConfigSend;
import com.ssafy.plant.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {
    @Autowired
    MqttConfigSend.OutboundGateway outboundGateway;
    @Value("${java.weather.secretKey}")
    private String secretKey;

//    @Cacheable(value = "getWeathers")
    public String getWeather() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);

        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + secretKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("500", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(formatedNow, "UTF-8")); /*‘21년 6월 28일발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8")); /*05시 발표*/
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode("35", "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode("126", "UTF-8")); /*예보지점의 Y 좌표값*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            Map<String, Object> map = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(sb.toString(), map.getClass());
            map = (Map<String, Object>) map.get("response");
            map = (Map<String, Object>) map.get("body");
            map = (Map<String, Object>) map.get("items");
            ArrayList<Map<String, String>> res = (ArrayList<Map<String, String>>) map.get("item");

            String baseTime = formatedNow;
            String fcstTime = "0600";
            String fcstDate = formatedNow;
            String SNO = "적설없음";
            String PCP = "강수없음";
            String TMP = "0";
            String SKY = "1";

            ArrayList<WeatherDTO> weathers = new ArrayList<>();
            for (Map<String, String> weather:res) {
                if (weather.get("fcstTime").equals(fcstTime)) {
                    if (weather.get("category").equals("TMP")){
                        TMP = weather.get("fcstValue");
                    } else if (weather.get("category").equals("SNO")){
                        SNO = weather.get("fcstValue");
                    } else if (weather.get("category").equals("PCP")){
                        PCP = weather.get("fcstValue");
                    } else if (weather.get("category").equals("SKY")){
                        SKY = weather.get("fcstValue");
                    }
                    fcstDate = weather.get("fcstDate");
                } else {
                    WeatherDTO weatherDTO = new WeatherDTO(baseTime, fcstDate, fcstTime, PCP, SNO, TMP, SKY);
                    if (weather.get("category").equals("TMP")) {
                        TMP = weather.get("fcstValue");
                    }
                    fcstTime = weather.get("fcstTime");
                    weathers.add(weatherDTO);
                }
            }

            String weathersJson = objectMapper.writeValueAsString(weathers);
            System.out.println(weathersJson);
            return weathersJson;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
}
