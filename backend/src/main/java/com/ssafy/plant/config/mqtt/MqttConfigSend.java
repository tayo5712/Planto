package com.ssafy.plant.config.mqtt;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
public class MqttConfigSend {
    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface OutboundGateway {
        void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);
    }
}
