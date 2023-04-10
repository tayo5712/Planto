package com.ssafy.plant.domain;

import com.ssafy.plant.dto.plant.PlantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long plantId;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToOne
    @JoinColumn(name="plant_dict_plant_dict_id")
    private DictEntity plantDict;

    @OneToOne
    @JoinColumn(name="pot_Entity_pot_id")
    private PotEntity potEntity;

    private String name;

    @Column(name="image_path")
    private String imagePath;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="create_date")
    private Date createDate;

    private int temperature;

    private int humidity;

    private int sun;

    private int soilMoisture;

    @Column(name = "water_level")
    private int waterLevel;

    private int attack;

    private int touch;

    private int liking;

    public void patch(Plant plant) {
        if (plant.getName() != null) {
            this.name = plant.getName();
        }
        if (plant.getCreateDate() != null) {
            this.createDate = plant.getCreateDate();
        }
        if (plant.getImagePath() != null) {
            this.imagePath = plant.getImagePath();
        }
        if (plant.getPlantDict() != this.plantDict && plant.getPlantDict() != null) {
            this.plantDict = plant.getPlantDict();
            this.liking = 50;
            this.humidity = 0;
            this.soilMoisture = 0;
            this.touch = 0;
            this.sun = 0;
            this.attack = 0;
            this.waterLevel = 0;
            this.temperature = 0;
        }
    }

    public PlantDto entityToDto() {
        return PlantDto.builder()
                .plantId(plantId)
                .plantDictId(plantDict.getPlantDictId())
                .potId(potEntity.getPotId())
                .name(name)
                .imagePath(imagePath)
                .createDate(createDate)
                .temperature(temperature)
                .humidity(humidity)
                .attack(attack)
                .sun(sun)
                .waterLevel(waterLevel)
                .touch(touch)
                .liking(liking)
                .soilMoisture(soilMoisture)
                .build();
    }
}

