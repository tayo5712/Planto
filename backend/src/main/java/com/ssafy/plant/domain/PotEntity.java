package com.ssafy.plant.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.plant.dto.PotDTO;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pot")
public class PotEntity {
    @Id
    @Column(name = "pot_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private long potId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public PotDTO entityToDto(){
        return PotDTO
                .builder()
                .potId(potId)
                .user(user)
                .plant(plant)
                .build();
    }

}
