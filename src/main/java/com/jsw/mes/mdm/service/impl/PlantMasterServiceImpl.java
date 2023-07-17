package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.exception.PlantMasterException;
import com.jsw.mes.mdm.mapper.PlantMapper;
import com.jsw.mes.mdm.model.PlantRequest;
import com.jsw.mes.mdm.model.PlantResponse;
import com.jsw.mes.mdm.repository.AppMasterRepository;
import com.jsw.mes.mdm.repository.PlantMasterRepository;
import com.jsw.mes.mdm.repository.UnitMasterRepository;
import com.jsw.mes.mdm.service.PlantMasterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;


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
    public PlantResponse addPlant(PlantRequest plantRequest) {

        if(plantMasterRepository.findAll().isEmpty()){
           return addingPlant(plantRequest);
        }else if(plantMasterRepository.findAll().
              stream().filter(plantMaster -> plantMaster.getPlantName().equals(plantRequest.getPlantName())).count() > 0 ){
          log.error("Plant Already exists with that name");
          throw new PlantMasterException("Plant Already exists with that name",HttpStatus.NOT_FOUND);
        }

            return addingPlant(plantRequest);

    }

    public PlantResponse addingPlant(PlantRequest plantRequest) {

        log.info("Plant Details are added");

        return this.plantMapper.mapToPlantResponse(
                this.plantMasterRepository.save(
                        this.plantMapper.mapToPlantMaster(plantRequest))) ;

    }

    @Override
    public PlantResponse updatePlant(PlantRequest plantRequest) {

        PlantMaster plantMaster=plantMasterRepository.findByPlantName(plantRequest.getPlantName());

        if(plantMaster == null) {
            log.error("Plant Already exists with that name");
            throw new PlantMasterException("Plant Credentials Not Found", HttpStatus.NOT_FOUND);
        } else{
            log.info("Plant credentials are updated");
            return this.plantMapper.mapToPlantResponse(plantMaster);
        }


    }

    @Override
    public String deletePlant(int plantId) {

        getPlant(plantId);

        plantMasterRepository.deleteById(plantId);

        log.info("Plant deleted successfully");

        return "Plant deleted successfully";

    }

    @Override
    public PlantResponse getPlant(int plantId) {

       return this.plantMapper.mapToPlantResponse(plantMasterRepository.findById(plantId)
                .orElseThrow( () -> new PlantMasterException("Plant Credentials Not Found with",HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<PlantResponse> getAllPlant() {

        List<PlantMaster> plantMasters= plantMasterRepository.findAll();

        if(plantMasters.isEmpty()){
            log.error("No Records Found");
            throw new PlantMasterException("No Records Found",HttpStatus.NOT_FOUND);
        }else {
            log.info("Records Found");
          return  this.plantMapper.mapToPlantResponseList(plantMasters);
        }

    }
}
