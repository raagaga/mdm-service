package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.exception.BadRequestException;
import com.jsw.mes.mdm.mapper.PlantMapper;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.repository.PlantRepository;
import com.jsw.mes.mdm.service.PlantMasterService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PlantServiceImpl implements PlantMasterService {

    private final PlantRepository plantRepository;

    private final PlantMapper plantMapper;

    public PlantServiceImpl(PlantRepository plantRepository,
                            PlantMapper plantMapper
    ) {
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
    }

  @Override
  public PlantMaster addPlant(PlantRequest plantRequest) {

    Optional<PlantMaster> plantMaster =
        plantRepository.findByPlantNameAndIsActive(plantRequest.getPlantName(), "Y");
    log.info("Query to fetch based on plantName & isActive from plant");

    if (!plantMaster.isEmpty()) {
        log.info("plantName already exists with the given name");
        throw new BadRequestException("plantName already exists with the given name");
    }
    plantRequest.setIsActive("Y");

    return !ObjectUtils.isEmpty(plantRequest)
        ? plantRepository.save(plantMapper.toEntity(plantRequest))
        : new PlantMaster();
  }

  @Override
  public PlantMaster updatePlant(PlantRequest plantRequest) {

    Optional<PlantMaster> plantMaster = plantRepository.findById(plantRequest.getPlantId());
      log.info("Query to fetch based on plantId from plant");

    if (plantMaster.isEmpty()) {
      throw new BadRequestException("Plant info not available");
    }

      Optional<PlantMaster> plantMasterOptional =
              plantRepository.findByPlantNameAndIsActive(plantRequest.getPlantName(), "Y");
      log.info("Query to fetch based on plantName & isActive from plant");

      if (!plantMaster.isEmpty() && plantMasterOptional.get().getPlantId() == plantRequest.getPlantId()) {
          log.info("plantName already exists with the given name");
          throw new BadRequestException("plantName already exists with the given name");
      }

    return plantRepository.save(

        PlantMaster.builder()
                .plantId(  plantMaster.get().getPlantId())
            .plantName(plantRequest.getPlantName())
            .isActive(plantRequest.getIsActive())
            .build());
  }

  @Override
  public PlantMaster deletePlant(int plantId) {

      PlantMaster plantMaster= plantRepository.findByPlantIdAndIsActive(plantId,"Y")
              .orElseThrow(() -> new BadRequestException("Plant info not available"));
      log.info("Query to fetch based on plantId & isActive from plant");

      plantMaster.setIsActive("N");
      log.info("plantMaster is setting as InActive");

      return plantRepository.save(plantMaster);

//        return plantRepository.updatePlant(plantId);
  }

    @Override
    public PlantMaster getPlant(int plantId) {

       return plantRepository.findByPlantIdAndIsActive(plantId,"Y")
                .orElseThrow(() ->  new BadRequestException("Plant info not available with the given plantId"));

    }

  @Override
  public List<PlantMaster> getAllPlant() {

        return plantRepository.findAllByIsActive("Y");
  }
}
