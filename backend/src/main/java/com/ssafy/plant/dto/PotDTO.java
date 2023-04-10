package com.ssafy.plant.dto;

import com.ssafy.plant.domain.Plant;
import com.ssafy.plant.domain.PotEntity;
import com.ssafy.plant.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PotDTO {

    private Long potId;

    private Plant plant;

    private User user;


    public PotEntity dtoToEntity(){
        return PotEntity.builder()
                .potId(this.potId)
                .plant(plant)
                .user(user)
                .build();
    }
}
