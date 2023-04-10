package com.ssafy.plant.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.ssafy.plant.domain.DictEntity;
import com.ssafy.plant.domain.Plant;
import com.ssafy.plant.domain.PotEntity;
import com.ssafy.plant.dto.plant.PlantDto;
import com.ssafy.plant.dto.plant.PlantRegistDto;
import com.ssafy.plant.repository.DictRepository;
import com.ssafy.plant.repository.PlantRepository;
import com.ssafy.plant.repository.PotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final PotRepository potRepository;
    private final DictRepository dictRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;

    @Transactional(readOnly = true)
    public PlantDto 식물상세보기(Long plantId) {
        Plant target = plantRepository.findByPlantId(plantId);
        PlantDto dto = target.entityToDto();
        return dto;
    }

    @Transactional
    public Plant 식물등록(PlantRegistDto plantRegistDto, Long potId) throws IOException {
        String imageFileName;
        if (plantRegistDto.getFile() == null) {
            imageFileName = "normalplant.jpg";
        } else {
            UUID uuid = UUID.randomUUID();
            imageFileName = uuid + "-" + plantRegistDto.getFile().getOriginalFilename();
            Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
            InputStream content = new ByteArrayInputStream(plantRegistDto.getFile().getBytes());
            bucket.create(imageFileName, content, plantRegistDto.getFile().getContentType());
        }
        System.out.println(imageFileName);
        // plant 테이블에 저장
        DictEntity dictEntity = dictRepository.findByPlantDictId(plantRegistDto.getPlantDictId());
        PotEntity potEntity = potRepository.findByPotId(potId);

        Plant plant = plantRegistDto.toEntity(imageFileName, potEntity, dictEntity);

//        // 포맷터
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
//        // 문자열 -> Date
//        Date date = formatter.parse(plantRegistDto.getCreateDate());
        potEntity.setPlant(plant);
        plantRepository.save(plant);
        potRepository.save(potEntity);
        return plant;
    }

    @Transactional
    public Plant 식물수정(PlantRegistDto plantRegistDto, Long plantId) throws IOException {
        Plant target = plantRepository.findByPlantId(plantId);

        if (target == null) {
            return null;
        }

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "-" + plantRegistDto.getFile().getOriginalFilename();
        System.out.println("이미지 파일 이름" + imageFileName);

        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream content = new ByteArrayInputStream(plantRegistDto.getFile().getBytes());
        bucket.create(imageFileName, content, plantRegistDto.getFile().getContentType());

        // plant 테이블에 저장
        DictEntity dictEntity = dictRepository.findByPlantDictId(plantRegistDto.getPlantDictId());
        PotEntity potEntity = target.getPotEntity();

        Plant plant = plantRegistDto.toEntity(imageFileName, potEntity, dictEntity);
        target.patch(plant);
        Plant updatedPlant = plantRepository.save(target);
        return updatedPlant;

    }

    @Transactional
    public Plant 식물삭제(Long plantId) {
        Plant target = plantRepository.findByPlantId(plantId);
        if (target == null) {
            return null;
        }
        plantRepository.delete(target);
        return target;
    }
}
