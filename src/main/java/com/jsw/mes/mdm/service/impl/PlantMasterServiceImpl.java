package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.exception.BadRequestException;
import com.jsw.mes.mdm.mapper.PlantMapper;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.repository.PlantMasterRepository;
import com.jsw.mes.mdm.service.PlantMasterService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PlantMasterServiceImpl implements PlantMasterService {

    private final PlantMasterRepository plantMasterRepository ;

    private final PlantMapper plantMapper;

    public PlantMasterServiceImpl(PlantMasterRepository plantMasterRepository,
                                  PlantMapper plantMapper
    ) {
        this.plantMasterRepository = plantMasterRepository;
        this.plantMapper = plantMapper;
    }

  @Override
  public PlantMaster addPlant(PlantRequest plantRequest) {
    Optional<PlantMaster> plantMaster =
        plantMasterRepository.findByPlantNameAndIsActive(plantRequest.getPlantName(), "Y");
    if (!plantMaster.isEmpty()) {
      throw new BadRequestException("please provide valid plantName");
    }
    plantRequest.setIsActive("Y");
    return !ObjectUtils.isEmpty(plantRequest)
        ? plantMasterRepository.save(createPlant(plantRequest))
        : new PlantMaster();
  }

    private PlantMaster createPlant(PlantRequest plantRequest) {
        return PlantMaster.builder().plantName(plantRequest.getPlantName()).isActive("Y").build();
    }

  @Override
  public PlantMaster updatePlant(PlantRequest plantRequest) {

    Optional<PlantMaster> plantMaster = plantMasterRepository.findById(plantRequest.getPlantId());
    if (plantMaster.isEmpty()) {
      throw new BadRequestException("Plant info not available");
    }
    return plantMasterRepository.save(

        PlantMaster.builder()
                .plantId(  plantMaster.get().getPlantId())
            .plantName(plantRequest.getPlantName())
            .isActive(plantRequest.getIsActive())
            .build());
  }

  @Override
  public PlantMaster deletePlant(int plantId) {
    return plantMasterRepository.updatePlant(plantId);
  }

    @Override
    public PlantMaster getPlant(int plantId) {
       return plantMasterRepository.findByPlantIdAndIsActive(plantId,"Y");
    }

  @Override
  public List<PlantMaster> getAllPlant() {
    return plantMasterRepository.findAllByIsActive("Y");
  }
}
