package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.exception.AppException;
import com.jsw.mes.mdm.exception.UnitException;
import com.jsw.mes.mdm.mapper.UnitMapper;
import com.jsw.mes.mdm.model.request.UnitRequest;
import com.jsw.mes.mdm.model.response.UnitResponse;
import com.jsw.mes.mdm.repository.AppRepository;
import com.jsw.mes.mdm.repository.PlantRepository;
import com.jsw.mes.mdm.repository.UnitRepository;
import com.jsw.mes.mdm.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UnitServiceImpl implements UnitService
{
    private final UnitRepository unitRepository ;
    private final PlantRepository plantRepository ;
    private final AppRepository appRepository;

    private final UnitMapper unitMapper;

    public UnitServiceImpl(UnitRepository unitRepository,
                           PlantRepository plantRepository, AppRepository appRepository, UnitMapper unitMapper
    ) {
        this.unitRepository = unitRepository;
        this.plantRepository = plantRepository;
        this.appRepository = appRepository;
        this.unitMapper = unitMapper;
    }

    @Override
    public UnitResponse addUnitMaster(UnitRequest unitRequest) {
        if(unitRequest.getUnitName().isEmpty())
        {
            log.error("Enter correct details...");
            throw new UnitException("Enter correct name...",HttpStatus.NOT_FOUND);
        }
        Optional<UnitMaster> unitMasterOptional = unitRepository.getByUnitName(unitRequest.getUnitName());
        if (unitMasterOptional.isPresent()) {
            log.error("UnitName already exists");
            throw new UnitException("UnitName already exists...",HttpStatus.NOT_FOUND);
        }
        if(!unitRequest.getIsActive().equals("Y")){
            log.error("Enter isActive as Y");
            throw new UnitException("Enter isActive as 'Y'",HttpStatus.NOT_FOUND);
        }
        if(!plantRepository.existsById(unitRequest.getPlantId()))
        {
            log.error("Enter correct ID's for plantMaster");
            throw new UnitException("Enter correct Plant ID",HttpStatus.NOT_FOUND);
        }

        if(!appRepository.existsById(unitRequest.getAppId()))
        {
            log.error("Enter correct ID's for appMaster");
            throw new UnitException("Enter correct App ID",HttpStatus.NOT_FOUND);
        }
        UnitMaster unitMaster = unitMapper.toEntity(unitRequest);

        PlantMaster plantMaster = plantRepository.findById(unitRequest.getPlantId()).get();
        plantMaster.getUnitMstList().add(unitMaster);

        AppMaster appMaster = appRepository.findById(unitRequest.getAppId()).get();
        appMaster.getUnitMstList().add(unitMaster);

        return unitMapper.toResponse(unitRepository.save(unitMaster), plantMaster.getPlantId(), appMaster.getAppId());
    }

    @Override
    public UnitResponse updateUnitMaster(UnitRequest unitRequest) {
        UnitMaster unitMaster1 = unitRepository.findById(unitRequest.getUnitId())
                .orElseThrow(() -> new UnitException("UnitId not found",HttpStatus.NOT_FOUND));

        if(!plantRepository.existsById(unitRequest.getPlantId()))
        {
            log.error("Enter correct ID's for plantMaster");
            throw new UnitException("Enter correct Plant ID",HttpStatus.NOT_FOUND);
        }

        if(!appRepository.existsById(unitRequest.getAppId()))
        {
            log.error("Enter correct ID's for appMaster");
            throw new UnitException("Enter correct App ID",HttpStatus.NOT_FOUND);
        }

        UnitMaster unitMaster2 = unitMapper.toEntity(unitRequest);

        if(unitRequest.getUnitName().isEmpty()) {
            log.error("Enter UnitName...");
            throw new UnitException("Enter UnitName...", HttpStatus.NOT_FOUND);
        }
        if(!unitRequest.getIsActive().equals("Y")){
            log.error("Enter isActive as Y");
            throw new UnitException("Enter isActive as 'Y'",HttpStatus.NOT_FOUND);
        }

        Optional<UnitMaster> optionalUnitMaster=unitRepository.getByUnitName(unitRequest.getUnitName());
        if(!optionalUnitMaster.isEmpty() && optionalUnitMaster.get().getUnitId() != unitRequest.getUnitId()){
            log.error("UnitName already exists in DB");
            throw new AppException("UnitName already exists",HttpStatus.NOT_FOUND);
        }

        unitMaster2.setUnitId(unitMaster1.getUnitId());
        unitRepository.save(unitMaster2);

        PlantMaster plantMaster = plantRepository.findById(unitRequest.getPlantId()).get();

        if (!plantMaster.getUnitMstList().stream().anyMatch(unitMaster -> unitMaster.getUnitId() == unitRequest.getUnitId())) {
            plantMaster.getUnitMstList().add(unitMaster2);
            plantRepository.save(plantMaster);
        }

        AppMaster appMaster = appRepository.findById(unitRequest.getAppId()).get();

        if (!appMaster.getUnitMstList().stream().anyMatch( unitMaster -> unitMaster.getUnitId() == unitRequest.getUnitId())) {
            appMaster.getUnitMstList().add(unitMaster2);
            appRepository.save(appMaster);
        }




        return unitMapper.toResponse(unitRepository.save(unitMaster2), plantMaster.getPlantId(), appMaster.getAppId());
    }

    @Override
    public UnitResponse deleteUnitMaster(int unitId) {
        UnitMaster master = unitRepository.findById(unitId)
                .orElseThrow(()->new UnitException("Id not found",HttpStatus.NOT_FOUND));
        master.setIsActive("N");
        return unitMapper.toResponse(unitRepository.save(master));
    }

    @Override
    public UnitResponse getUnitMaster(int unitId) {
        UnitMaster unitMaster = unitRepository.findById(unitId)
                .orElseThrow(()-> new UnitException("UnitId not found",HttpStatus.NOT_FOUND));

        List<PlantMaster> plantMasterList = plantRepository.findAll()
                .stream().filter(plantMaster -> plantMaster.getUnitMstList().stream().anyMatch(unit -> unit.getUnitId()
                        == unitId)).collect(Collectors.toList());

        List<AppMaster> appMasterList = appRepository.findAll()
                .stream().filter(appMaster -> appMaster.getUnitMstList().stream().anyMatch(unit -> unit.getUnitId()
                        == unitId)).collect(Collectors.toList());

        return unitMapper.toResponse(unitMaster,
                plantMasterList.get(0).getPlantId(),appMasterList.get(0).getAppId());
    }

    @Override
    public List<UnitResponse> getAllUnitMaster(int plantId, long appId) {

        List<UnitMaster> plantUnitMasterList =plantRepository.findById(plantId).get().getUnitMstList();
        log.info("Getting all PlantList's based on appId in AppMaster ");

        Set<UnitMaster> appUnitMasterSet =appRepository.findById(appId).get().getUnitMstList()
                .stream()
                .filter(plantUnitMasterList::contains)
                .collect(Collectors.toSet());

        return appUnitMasterSet.stream()
                .map(unitMaster -> unitMapper.toResponse(unitMaster,plantId,appId)).collect(Collectors.toList());
    }
}