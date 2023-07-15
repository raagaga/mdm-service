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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlantMasterServiceImpl implements PlantMasterService {

    @Autowired
    private AppMasterRepository appMasterRepository;

    private final PlantMasterRepository plantMasterRepository;

    @Autowired
    private UnitMasterRepository unitMasterRepository;

    private final PlantMapper plantMapper;

    public PlantMasterServiceImpl(PlantMasterRepository plantMasterRepository,
                                  PlantMapper plantMapper
    ) {
        this.plantMasterRepository = plantMasterRepository;
        this.plantMapper = plantMapper;
    }

    @Override
    public PlantResponse addPlant(PlantRequest plantRequest) {

      if(plantMasterRepository.findAll().
              stream().filter(plantMaster -> plantMaster.getPlantName().equals(plantRequest.getPlantName())).collect(Collectors.toList()).size() > 0 )
      {
          throw new PlantMasterException("Plant Already exists with this name",HttpStatus.NOT_FOUND);
        }

            return this.plantMapper.mapPlantResponse(
                    this.plantMasterRepository.save(
                            this.plantMapper.mapPlantMaster(plantRequest))) ;

    }

    @Override
    public PlantResponse updatePlant(PlantRequest plantRequest) {

        PlantMaster plantMaster=plantMasterRepository.findByPlantName(plantRequest.getPlantName());

        if(plantMaster == null)
            throw new PlantMasterException("Plant Credentials Not Found",HttpStatus.NOT_FOUND);
        else
           return this.plantMapper.mapPlantResponse(plantMaster);

    }

    @Override
    public String deletePlant(int plantId) {

        getPlant(plantId);

        plantMasterRepository.deleteById(plantId);

        return "Plant deleted successfully";

    }

    @Override
    public PlantResponse getPlant(int plantId) {

       return this.plantMapper.mapPlantResponse(plantMasterRepository.findById(plantId)
                .orElseThrow( () -> new PlantMasterException("Plant Credentials Not Found with",HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<PlantResponse> getAllPlant() {

        List<PlantResponse> plantResponses=new ArrayList<>();

        List<PlantMaster> plantMasters= plantMasterRepository.findAll();

        if(plantMasters.isEmpty()){
            throw new PlantMasterException("No Records Found",HttpStatus.NOT_FOUND);
        }else {
            plantMasters.stream().forEach( plantMaster -> {
                plantResponses.add(this.plantMapper.mapPlantResponse(plantMaster));
            });
        }

        return plantResponses;
    }
}
