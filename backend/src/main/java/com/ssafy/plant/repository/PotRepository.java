package com.ssafy.plant.repository;

import com.ssafy.plant.domain.Plant;
import com.ssafy.plant.domain.PotEntity;
import com.ssafy.plant.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PotRepository extends JpaRepository<PotEntity, Long> {
    PotEntity findByPotId(Long potId);
    List<PotEntity> findByUser(User user);
}
