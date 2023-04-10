package com.ssafy.plant.service;


import com.ssafy.plant.domain.DictEntity;
import com.ssafy.plant.dto.DictDTO;
import com.ssafy.plant.repository.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictService {

    @Autowired
    private DictApi dictApi;

    @Autowired
    private DictRepository dictRepository;

    public void getDictApi(){
//        page 가져오기
        for (int i = 1; i <= 22; i++) {
            dictApi.callApi(Integer.toString(i), "", "gardenList", "list");
        }
    }

    public List<DictDTO> getDictList() {
        List<DictEntity> dictEntities = dictRepository.findAll();
        List<DictDTO> dictDTOs = new ArrayList<>();
        for (DictEntity entity : dictEntities) {
            DictDTO dictDTO = entity.entityToDto();
            dictDTOs.add(dictDTO);
        }
        return dictDTOs;
    }

    public List<DictDTO> getDictListLevel(String level) {
        if (level.equals("beginner")){
            level = "초보자";
        } else if (level.equals("intermediate")){
            level = "경험자";
        } else if (level.equals("advanced")) {
            level = "전문가";
        }
        List<DictDTO> dictDTOs = new ArrayList<>();
        List<DictEntity> dictEntities = dictRepository.findByManageLevel(level);

        for (DictEntity dictEntity : dictEntities) {
            dictDTOs.add(dictEntity.entityToDto());
        }

        return dictDTOs;
    }

    public DictDTO getDictDetail(String plantDictId) {
        long id = Integer.parseInt(plantDictId);
        return dictRepository.findById(id).get().entityToDto();
    }

    public List<DictDTO> getDictDark(){
        List<DictDTO> dictDTOS = new ArrayList<>();
        List<DictEntity> dictEntities = dictRepository.findByLightMax(800);

        for (DictEntity dictEntity : dictEntities) {
            dictDTOS.add(dictEntity.entityToDto());
        }

        return dictDTOS;
    }

    public List<DictDTO> getDictWater(){
        List<DictDTO> dictDTOS = new ArrayList<>();
        List<DictEntity> dictEntities = dictRepository.findByHumidityMaxLessThanEqual(40);

        for (DictEntity entity : dictEntities){
            dictDTOS.add(entity.entityToDto());
        }

        return dictDTOS;
    }
}
