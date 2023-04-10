package com.ssafy.plant.dto;

import com.ssafy.plant.domain.AchievementEntity;
import com.ssafy.plant.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserAchievementDTO {
    private int user_achievement_id;

    private AchievementEntity achievement;

    private User user;

    private LocalDateTime created;
}
