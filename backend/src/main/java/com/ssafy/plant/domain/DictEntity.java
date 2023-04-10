package com.ssafy.plant.domain;

import com.ssafy.plant.dto.DictDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Table(name = "plant_dict")
public class DictEntity {
    @Id
    @Column(name = "plant_dict_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private long plantDictId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "manage_level", length = 100)
    private String manageLevel;

    @Column(name = "function_info", length = 5000)
    private String functionInfo;

    @Column(name = "temperature_max")
    private int temperatureMax;

    @Column(name = "temperature_min")
    private int temperatureMin;

    @Column(name = "light_max")
    private int lightMax;

    @Column(name = "light_min")
    private int lightMin;

    @Column(name = "humidity_max")
    private int humidityMax;

    @Column(name = "humidity_min")
    private int humidityMin;

    @Column(name = "manage_info", length = 5000)
    private String manageInfo;

    @Column(name = "advice_info", length = 5000)
    private String adviceInfo;
    @Column(name = "image_path", length = 200)
    private String imagePath;

    @Column(name = "plant_eng", length = 200)
    private String plantEng;

    @Builder
    public DictEntity(long plantDictId, String name, String manageLevel, String functionInfo, int temperatureMax, int temperatureMin,
                      int lightMax, int lightMin, int humidityMax, int humidityMin, String manageInfo, String adviceInfo, String imagePath,
                      String plantEng) {
        this.plantDictId = plantDictId;
        this.name = name;
        this.manageLevel = manageLevel;
        this.functionInfo = functionInfo;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.lightMax = lightMax;
        this.lightMin = lightMin;
        this.humidityMax = humidityMax;
        this.humidityMin = humidityMin;
        this.manageInfo = manageInfo;
        this.adviceInfo = adviceInfo;
        this.imagePath = imagePath;
        this.plantEng = plantEng;
    }

    public DictDTO entityToDto(){
        return DictDTO.builder()
                .plantDictId(plantDictId)
                .name(name)
                .manageLevel(manageLevel)
                .functionInfo(functionInfo)
                .temperatureMax(temperatureMax)
                .temperatureMin(temperatureMin)
                .lightMax(lightMax)
                .lightMin(lightMin)
                .humidityMax(humidityMax)
                .humidityMin(humidityMin)
                .manageInfo(manageInfo)
                .adviceInfo(adviceInfo)
                .imagePath(imagePath)
                .plantEng(plantEng)
                .build();
    }
}
