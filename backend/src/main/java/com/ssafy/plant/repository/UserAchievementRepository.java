package com.ssafy.plant.repository;

import com.ssafy.plant.domain.AchievementEntity;
import com.ssafy.plant.domain.User;
import com.ssafy.plant.domain.UserAchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAchievementRepository extends JpaRepository<UserAchievementEntity, Integer> {
    List<UserAchievementEntity> findByUserOrderByCreatedDesc(User user);
    List<UserAchievementEntity> findByUser(User user);
}
