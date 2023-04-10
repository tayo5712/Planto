package com.ssafy.plant.repository;

import com.ssafy.plant.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant findByPlantId(Long plantId);
}
