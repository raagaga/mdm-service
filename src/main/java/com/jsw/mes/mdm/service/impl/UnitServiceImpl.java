package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.exception.UnitNotFoundException;
import com.jsw.mes.mdm.mapper.UnitMapper;
import com.jsw.mes.mdm.model.request.UnitRequest;
import com.jsw.mes.mdm.model.response.UnitResponse;
import com.jsw.mes.mdm.repository.AppMasterRepository;
import com.jsw.mes.mdm.repository.PlantMasterRepository;
import com.jsw.mes.mdm.repository.UnitRepository;
import com.jsw.mes.mdm.service.UnitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UnitServiceImpl implements UnitService
{
    private final UnitRepository unitRepository ;
    private final PlantMasterRepository plantMasterRepository ;
    private final AppMasterRepository appMasterRepository;

    private final UnitMapper unitMapper;

    public UnitServiceImpl(UnitRepository unitRepository,
                           PlantMasterRepository plantMasterRepository, AppMasterRepository appMasterRepository, UnitMapper unitMapper
    ) {
        this.unitRepository = unitRepository;
        this.plantMasterRepository = plantMasterRepository;
        this.appMasterRepository = appMasterRepository;
        this.unitMapper = unitMapper;
    }

    @Override
    public UnitResponse addUnitMaster(UnitRequest unitRequest) {
        if(unitRequest.getUnitName().isEmpty())
        {
            log.error("Enter correct details...");
            throw new UnitNotFoundException("Enter correct name...",HttpStatus.NOT_FOUND);
        }
        UnitMaster unitMasterOptional = unitRepository.getByUnitName(unitRequest.getUnitName())
                .orElseThrow(() -> new UnitNotFoundException("UnitMaster already exists...",HttpStatus.NOT_FOUND));

        if(!unitRequest.getIsActive().equals("Y")){
            log.error("Enter isActive as Y");
            throw new UnitNotFoundException("Enter isActive as 'Y'",HttpStatus.NOT_FOUND);
        }
        if(!plantMasterRepository.existsById(unitRequest.getPlantId()))
        {
            log.error("Enter correct ID's for plantMaster");
            throw new UnitNotFoundException("Enter correct Plant ID",HttpStatus.NOT_FOUND);
        }

        if(!appMasterRepository.existsById(unitRequest.getAppId()))
        {
            log.error("Enter correct ID's for appMaster");
            throw new UnitNotFoundException("Enter correct App ID",HttpStatus.NOT_FOUND);
        }
        UnitMaster unitMaster = unitMapper.toEntity(unitRequest);

        PlantMaster plantMaster = plantMasterRepository.findById(unitRequest.getPlantId()).get();
        plantMaster.getUnitMstList().add(unitMaster);

        AppMaster appMaster = appMasterRepository.findById(unitRequest.getAppId()).get();
        appMaster.getUnitMstList().add(unitMaster);

        return unitMapper.toResponse(unitRepository.save(unitMaster), plantMaster.getPlantId(), appMaster.getAppId());
    }

    @Override
    public UnitResponse updateUnitMaster(UnitRequest unitRequest) {

        if(unitRequest.getUnitName().isEmpty())
        {
            log.error("Enter correct details...");
            throw new UnitNotFoundException("Enter correct name...",HttpStatus.NOT_FOUND);
        }
        UnitMaster unitMasterOptional = unitRepository.getByUnitName(unitRequest.getUnitName())
                .orElseThrow(() -> new UnitNotFoundException("UnitMaster already exists...",HttpStatus.NOT_FOUND));

        if(!unitRequest.getIsActive().equals("Y")){
            log.error("Enter isActive as Y");
            throw new UnitNotFoundException("Enter isActive as 'Y'",HttpStatus.NOT_FOUND);
        }
        if(!plantMasterRepository.existsById(unitRequest.getPlantId()))
        {
            log.error("Enter correct ID's for plantMaster");
            throw new UnitNotFoundException("Enter correct Plant ID",HttpStatus.NOT_FOUND);
        }

        if(!appMasterRepository.existsById(unitRequest.getAppId()))
        {
            log.error("Enter correct ID's for appMaster");
            throw new UnitNotFoundException("Enter correct App ID",HttpStatus.NOT_FOUND);
        }

        UnitMaster unitMaster = unitMapper.toEntity(unitRequest);

        PlantMaster plantMaster = plantMasterRepository.findById(unitRequest.getPlantId()).get();
        plantMaster.getUnitMstList().add(unitMaster);

        AppMaster appMaster = appMasterRepository.findById(unitRequest.getAppId()).get();
        appMaster.getUnitMstList().add(unitMaster);

        return unitMapper.toResponse(unitRepository.save(unitMaster),plantMaster.getPlantId(), appMaster.getAppId());
    }

    @Override
    public UnitResponse deleteUnitMaster(int unitId) {
        UnitMaster master = unitRepository.findById(unitId)
                .orElseThrow(()->new UnitNotFoundException("Id not found",HttpStatus.NOT_FOUND));
        master.setIsActive("N");
        return unitMapper.toResponse(unitRepository.save(master));
    }

    @Override
    public UnitResponse getUnitMaster(int unitId) {
        UnitMaster unitMaster = unitRepository.findById(unitId)
                .orElseThrow(()-> new UnitNotFoundException("UnitId not found",HttpStatus.NOT_FOUND));

        List<PlantMaster> plantMasterList = plantMasterRepository.findAll()
                .stream().filter(plantMaster -> plantMaster.getUnitMstList().stream().anyMatch(unit -> unit.getUnitId()
                        == unitId)).collect(Collectors.toList());

        List<AppMaster> appMasterList = appMasterRepository.findAll()
                .stream().filter(appMaster -> appMaster.getUnitMstList().stream().anyMatch(unit -> unit.getUnitId()
                        == unitId)).collect(Collectors.toList());

        return unitMapper.toResponse(unitMaster,
                plantMasterList.get(0).getPlantId(),appMasterList.get(0).getAppId());
    }

    @Override
    public List<UnitResponse> getAllUnitMaster(int plantId, long appId) {
        List<UnitMaster> unitMasterList = unitRepository.findAll();
        if(unitMasterList.isEmpty())
        {
            log.error("No unit's found...");
            throw new UnitNotFoundException("UnitMasters not found",HttpStatus.NOT_FOUND);
        }
        return unitMasterList.stream().map(unitMaster -> getUnitMaster(unitMaster.getUnitId())).collect(Collectors.toList());
    }
}
