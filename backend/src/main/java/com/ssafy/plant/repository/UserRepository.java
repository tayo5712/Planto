package com.ssafy.plant.repository;

import com.ssafy.plant.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findBySocialId(String socialId);
    User findByUserId(Long userId);
}
