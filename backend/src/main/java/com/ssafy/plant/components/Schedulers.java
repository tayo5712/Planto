package com.ssafy.plant.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.plant.config.mqtt.MqttConfigSend;
import com.ssafy.plant.service.LikingService;
import com.ssafy.plant.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedulers {
    @Autowired
    MqttConfigSend.OutboundGateway outboundGateway;

    @Autowired
    WeatherService weatherService;

    @Autowired
    LikingService likingService;

    @Scheduled(cron = "*/10 * * * * *")
    public void getWeather(){
        String weathers = weatherService.getWeather();
        outboundGateway.sendToMqtt(weathers, "STM");
    }

//    @Scheduled(cron = "*/10 * * * * *")
//    public void sendLiking() throws JsonProcessingException {
//        likingService.sendLiking(123, 2);
//    }
//
//    @Scheduled(cron = "30 * * * * *")
//    public void sendLiking1() throws JsonProcessingException {
//        likingService.sendLiking(123, 1);
//    }
//
//    @Scheduled(cron = "10 * * * * *")
//    public void sendLiking2() throws JsonProcessingException {
//        likingService.sendLiking(123, 0);
//    }
}
