package com.ssafy.plant.controller;

import com.ssafy.plant.domain.Plant;
import com.ssafy.plant.dto.plant.PlantDto;
import com.ssafy.plant.dto.plant.PlantRegistDto;
import com.ssafy.plant.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plant")
public class PlantController {

    private final PlantService plantService;

    @GetMapping("/{plantId}")
    public ResponseEntity<PlantDto> getPlant(@PathVariable Long plantId) {
        PlantDto plantDto = plantService.식물상세보기(plantId);
        return ResponseEntity.status(HttpStatus.OK).body(plantDto);
    }

    @PostMapping("/{potId}")
    public ResponseEntity<String> createPlant(PlantRegistDto dto, @PathVariable Long potId) throws IOException {
        System.out.println(dto);
        plantService.식물등록(dto, potId);
        return ResponseEntity.status(HttpStatus.CREATED).body("식물등록완료");
    }

    @PutMapping("/{plantId}")
    public ResponseEntity<String> updatePlant(@PathVariable Long plantId, PlantRegistDto plantRegistDto) throws IOException {
        System.out.println(plantRegistDto);
        Plant plant = plantService.식물수정(plantRegistDto, plantId);
        return ResponseEntity.status(HttpStatus.OK).body("식물수정완료");
    }

    @DeleteMapping("/{plantId}")
    public ResponseEntity<String> deletePlant(@PathVariable Long plantId) {
        Plant plantEntity = plantService.식물삭제(plantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("식물삭제완료");
    }

//    @DeleteMapping("/files")
//    public ResponseEntity<String> deleteFile(String name) throws IOException {
//        Bucket bucket = StorageClient.getInstance().bucket();
//
//        if (StringUtils.isEmpty(name)) {
//            throw new IOException("invalid file name");
//        }
//
//        Blob blob = bucket.get(name);
//
//        if (blob == null) {
//            throw new IOException("file not found");
//        }
//
//        blob.delete();
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제");
//    }
}
