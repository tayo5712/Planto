package com.ssafy.plant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DictDTO {
    private long plantDictId;

    private String name;

    private String manageLevel;

    private String functionInfo;

    private int temperatureMax;

    private int temperatureMin;

    private int lightMax;

    private int lightMin;

    private int humidityMax;

    private int humidityMin;

    private String manageInfo;

    private String adviceInfo;
    private String imagePath;

    private String plantEng;
    @Builder
    public DictDTO(long plantDictId, String name, String manageLevel, String functionInfo, int temperatureMax, int temperatureMin,
                   int lightMax, int lightMin, int humidityMax, int humidityMin, String manageInfo, String adviceInfo, String imagePath,
                   String plantEng){
        this.plantDictId = plantDictId;
        this.name = name;
        this.manageLevel = manageLevel;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.lightMax = lightMax;
        this.lightMin = lightMin;
        this.humidityMax = humidityMax;
        this.humidityMin = humidityMin;
        this.functionInfo = functionInfo;
        this.manageInfo = manageInfo;
        this.adviceInfo = adviceInfo;
        this.imagePath = imagePath;
        this.plantEng = plantEng;
    }
}
