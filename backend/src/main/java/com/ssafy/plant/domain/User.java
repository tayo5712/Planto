package com.ssafy.plant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

//    @JsonIgnoreProperties({"user"})
//    @OneToMany(mappedBy ="user", fetch=FetchType.LAZY)
//    private List<Plant> plants;

    @Column(nullable = false, unique = true, name = "socialId")
    private String socialId;

    @Column(nullable = false)
    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    private String role;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public User(String socialId, String name, String profileImageUrl, String role) {
        this.socialId = socialId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }
}
