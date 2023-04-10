package com.ssafy.plant.repository;

import com.ssafy.plant.domain.AchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<AchievementEntity, Integer> {
    AchievementEntity findByImageName(String name);
}
