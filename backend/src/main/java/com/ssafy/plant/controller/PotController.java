package com.ssafy.plant.controller;

import com.ssafy.plant.domain.PotEntity;
import com.ssafy.plant.domain.User;
import com.ssafy.plant.dto.PotDTO;
import com.ssafy.plant.service.PotService;
import com.ssafy.plant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pot")
public class PotController {

    @Autowired
    PotService potService;

    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<String> setPot(HttpServletRequest request, @RequestBody PotDTO potDTO){
        User user = userService.getUser(request);
        String result = potService.setPot(potDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping("")
    public ResponseEntity<List<PotDTO>> getPot(HttpServletRequest request) {
        User user = userService.getUser(request);
        List<PotDTO> potDTOS = potService.getPots(user);
        return ResponseEntity.status(HttpStatus.OK).body(potDTOS);
    }
}
