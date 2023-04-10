package com.ssafy.plant.domain;

import com.ssafy.plant.dto.UserAchievementDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_achievement")
@Builder
public class UserAchievementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_achievement_id")
    private int user_achievement_id;

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private AchievementEntity achievement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created")
    private LocalDateTime created;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void created() {
        this.created = LocalDateTime.now();
    }

    public UserAchievementDTO entityToDTO(){
        return UserAchievementDTO.builder()
                .user_achievement_id(user_achievement_id)
                .achievement(achievement)
                .user(user)
                .created(created)
                .build();
    }
}
