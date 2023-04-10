package com.ssafy.plant.dto.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantDto {
    @JsonProperty("plant_id")
    private Long plantId;
    @JsonProperty("plant_dict_plant_dict_id")
    private Long plantDictId;
    @JsonProperty("pot_id")
    private Long potId;
    private String name;
    private String imagePath;
    private Date createDate;
    private int temperature;
    private int humidity;
    private int sun;
    @JsonProperty("water_level")
    private int waterLevel;
    private int touch;
    private int attack;
    private int liking;
    private int soilMoisture;
}
