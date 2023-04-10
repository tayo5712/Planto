package com.ssafy.plant.repository;

import com.ssafy.plant.domain.DictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictRepository extends JpaRepository<DictEntity, Long> {
    List<DictEntity> findByManageLevel(String level);
    List<DictEntity> findByLightMax(int light);
    List<DictEntity> findByHumidityMaxLessThanEqual(int humidityMax);

    DictEntity findByPlantDictId(Long plantDictId);

}
