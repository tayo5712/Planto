package com.ssafy.plant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.ssafy.plant.domain.DictEntity;
import com.ssafy.plant.dto.DictDTO;
import com.ssafy.plant.repository.DictRepository;
import com.ssafy.plant.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dict")
public class DictController {

    @Autowired
    DictService dictService;

    @Autowired
    DictRepository dictRepository;

    @GetMapping("")
    public ResponseEntity<List<DictDTO>> dictList(){
        return ResponseEntity.status(HttpStatus.OK).body(dictService.getDictList());
    }

    @GetMapping("/{level}")
    public ResponseEntity<List<DictDTO>> dictListLeve(@PathVariable("level") String level){
        return ResponseEntity.status(HttpStatus.OK).body(dictService.getDictListLevel(level));
    }

    @GetMapping("/detail/{plantDictId}")
    public ResponseEntity<DictDTO> dictDetail(@PathVariable("plantDictId") String plantDictId) {
        return ResponseEntity.status(HttpStatus.OK).body(dictService.getDictDetail(plantDictId));
    }

    @GetMapping("/dark")
    public ResponseEntity<List<DictDTO>> dictDark(){
        return ResponseEntity.status(HttpStatus.OK).body(dictService.getDictDark());
    }

    @GetMapping("/water")
    public ResponseEntity<List<DictDTO>> dictWater(){
        return ResponseEntity.status(HttpStatus.OK).body(dictService.getDictWater());
    }

}
