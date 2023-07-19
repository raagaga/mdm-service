package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.exception.BadRequestException;
import com.jsw.mes.mdm.exception.ProcessMasterException;
import com.jsw.mes.mdm.mapper.ProcessMasterMapper;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.request.ProcessMasterRequest;
import com.jsw.mes.mdm.model.response.ProcessMasterResponse;
import com.jsw.mes.mdm.repository.AppMasterRepository;
import com.jsw.mes.mdm.repository.ProcessMasterRepository;
import com.jsw.mes.mdm.repository.UnitMasterRepository;
import com.jsw.mes.mdm.service.ProcessMasterService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProcessMasterServiceImpl implements ProcessMasterService {

    private final AppMasterRepository appMasterRepository;

    private final UnitMasterRepository unitMasterRepository;

    private final ProcessMasterRepository processMasterRepository;

    private final ProcessMasterMapper processMasterMapper;

    public ProcessMasterServiceImpl(AppMasterRepository appMasterRepository, UnitMasterRepository unitMasterRepository, ProcessMasterRepository processMasterRepository, ProcessMasterMapper processMasterMapper) {
        this.appMasterRepository = appMasterRepository;
        this.unitMasterRepository = unitMasterRepository;
        this.processMasterRepository = processMasterRepository;
        this.processMasterMapper = processMasterMapper;
    }

    @Override
    public ProcessMasterResponse addProcess(ProcessMasterRequest processMasterRequest) {

        processMasterRequest.setIsActive("Y");

        Optional<ProcessMaster> processMasterOptional =processMasterRepository.findByProcessNameAndIsActive(processMasterRequest.getProcessName(),"Y");

        if(!processMasterOptional.isEmpty()){
            log.error("Process Already exists with the given processName");
            throw new ProcessMasterException("Process Already exists with the given processName", HttpStatus.NOT_FOUND);
        }

        ProcessMaster processMaster1=processMasterMapper.toEntity(processMasterRequest);

        AppMaster appMaster = getAppMaster(processMasterRequest.getProcessId());

        UnitMaster unitMaster=getUnitMaster(processMasterRequest.getProcessId());

        processMasterRepository.save(processMaster1);

        unitMaster.getProcessMstList().add(processMaster1);
        unitMasterRepository.save(unitMaster);

        appMaster.getProcessMstList().add(processMaster1);
        appMasterRepository.save(appMaster);

        return processMasterMapper.toResponse(processMaster1,appMaster.getAppId(),unitMaster.getUnitId());

    }

    public AppMaster getAppMaster(int id){
       return appMasterRepository.findByAppIdAndIsActive(id,"Y")
                .orElseThrow( () -> new ProcessMasterException("AppId NotFound", HttpStatus.NOT_FOUND));
    }

    public UnitMaster getUnitMaster(int id){
       return unitMasterRepository.findByUnitIdAndIsActive(id,"Y")
                .orElseThrow( () -> new ProcessMasterException("UnitIdFound", HttpStatus.NOT_FOUND));
    }

    @Override
    public ProcessMasterResponse updateProcess(ProcessMasterRequest processMasterRequest) {

       ProcessMaster processMaster =processMasterRepository.findById(processMasterRequest.getProcessId())
               .orElseThrow(()-> new ProcessMasterException("Process does not exists with the given processName", HttpStatus.NOT_FOUND));

       AppMaster appMaster = getAppMaster(processMasterRequest.getProcessId());

       UnitMaster unitMaster=getUnitMaster(processMasterRequest.getProcessId());

        ProcessMaster mapperProcessMaster=processMasterMapper.toEntity(processMasterRequest);
        mapperProcessMaster.setProcessId(processMasterRequest.getProcessId());
        processMasterRepository.save(mapperProcessMaster);

        if (!unitMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processMasterRequest.getProcessId())) {
            unitMaster.getProcessMstList().add(mapperProcessMaster);
            unitMasterRepository.save(unitMaster);
        }

        if (!appMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processMasterRequest.getProcessId())) {
            appMaster.getProcessMstList().add(processMaster);
            appMasterRepository.save(appMaster);
        }

        return processMasterMapper.toResponse(mapperProcessMaster,appMaster.getAppId(),unitMaster.getUnitId());
    }

    @Override
    public ProcessMasterResponse deleteProcess(int processId) {

        ProcessMaster processMaster =processMasterRepository.findById(processId)
                .orElseThrow(()-> new ProcessMasterException("Process does not exists with the given processName", HttpStatus.NOT_FOUND));

        processMaster.setIsActive("N");

        return processMasterMapper.toResponse(processMasterRepository.save(processMaster));
    }

    @Override
    public ProcessMasterResponse getProcess(int processId) {

        ProcessMaster processMaster =processMasterRepository.findById(processId)
                .orElseThrow(()-> new ProcessMasterException("Process does not exists with the given processName", HttpStatus.NOT_FOUND));

        List<UnitMaster> unitMasterList = unitMasterRepository.findAll()
                .stream()
                .filter(unitMaster -> unitMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processId))
                .collect(Collectors.toList());

        List<AppMaster> appMasterList = appMasterRepository.findAll()
                .stream()
                .filter(appMaster -> appMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processId))
                .collect(Collectors.toList());

        return    processMasterMapper.toResponse(processMaster,
                appMasterList.get(0).getAppId(),
                unitMasterList.get(0).getUnitId());

    }

    @Override
    public List<ProcessMasterResponse> getAllProcess() {

        List<ProcessMaster> processMasters = processMasterRepository.findAll();

        if (processMasters.isEmpty()) {
            throw new ProcessMasterException("No records Found", HttpStatus.NOT_FOUND);
        }

        return processMasters.stream()
                .map(processMaster -> getProcess(processMaster.getProcessId())).collect(Collectors.toList());
    }


}
