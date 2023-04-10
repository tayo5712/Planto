package com.ssafy.plant.service;

import com.ssafy.plant.domain.AchievementEntity;
import com.ssafy.plant.domain.User;
import com.ssafy.plant.domain.UserAchievementEntity;
import com.ssafy.plant.dto.UserAchievementDTO;
import com.ssafy.plant.repository.AchievementRepository;
import com.ssafy.plant.repository.UserAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAchievementService {
    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    UserAchievementRepository userAchievementRepository;

    public List<UserAchievementDTO> getAchievements(User user){
        List<UserAchievementEntity> userAchievementEntities= userAchievementRepository.findByUserOrderByCreatedDesc(user);
        List<UserAchievementDTO> userAchievementDTOS = new ArrayList<>();

        for (UserAchievementEntity userAchievementEntity : userAchievementEntities){
            userAchievementDTOS.add(userAchievementEntity.entityToDTO());
        }

        return userAchievementDTOS;
    }

    public UserAchievementDTO setAchievement(User user, String name){
        AchievementEntity achievement = achievementRepository.findByImageName(name);

        UserAchievementEntity userAchievementEntity = UserAchievementEntity
                .builder()
                .user(user)
                .achievement(achievement)
                .build();

        UserAchievementEntity userAchievementEntity1 = userAchievementRepository.save(userAchievementEntity);

        return userAchievementEntity1.entityToDTO();
    }
}
