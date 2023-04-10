package com.ssafy.plant.controller;

import com.ssafy.plant.domain.User;
import com.ssafy.plant.dto.UserAchievementDTO;
import com.ssafy.plant.service.UserAchievementService;
import com.ssafy.plant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/v1/achievement")
public class AchievementController {

    @Autowired
    UserService userService;

    @Autowired
    UserAchievementService userAchievementService;

    @GetMapping("")
    public ResponseEntity<List<UserAchievementDTO>> getAchievement(HttpServletRequest request){
        User user = userService.getUser(request);
        List<UserAchievementDTO> userAchievementDTOList = userAchievementService.getAchievements(user);

        return ResponseEntity.status(HttpStatus.OK).body(userAchievementDTOList);
    }

    @PostMapping("/{achievement_name}")
    public ResponseEntity<UserAchievementDTO> setAchievement(HttpServletRequest request, @PathVariable String achievement_name) {
        User user = userService.getUser(request);
        UserAchievementDTO userAchievementDTO = userAchievementService.setAchievement(user, achievement_name);

        return ResponseEntity.status(HttpStatus.CREATED).body(userAchievementDTO);
    }
}
