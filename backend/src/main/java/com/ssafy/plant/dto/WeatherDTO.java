package com.ssafy.plant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class WeatherDTO {
    private String id;

    private String baseDate;

    private String fcstDate;

    private String fcstTime;

    private String PCP;

    private String SNO;

    private String TMP;

    private String SKY;

    @Builder
    public WeatherDTO(String baseDate, String fcstDate, String fcstTime, String PCP, String SNO, String TMP, String SKY) {
        this.id = baseDate + fcstDate + fcstTime;
        this.baseDate = baseDate;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.PCP = PCP;
        this.SNO = SNO;
        this.TMP = TMP;
        this.SKY = SKY;
    }
}
