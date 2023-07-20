package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.exception.ProcessMasterException;
import com.jsw.mes.mdm.mapper.ProcessMasterMapper;
import com.jsw.mes.mdm.model.request.ProcessMasterRequest;
import com.jsw.mes.mdm.model.response.ProcessMasterResponse;
import com.jsw.mes.mdm.model.response.QueryResponse;
import com.jsw.mes.mdm.repository.AppMasterRepository;
import com.jsw.mes.mdm.repository.ProcessMasterRepository;
import com.jsw.mes.mdm.repository.UnitMasterRepository;
import com.jsw.mes.mdm.service.ProcessMasterService;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProcessMasterServiceImpl implements ProcessMasterService {

    private final AppMasterRepository appMasterRepository;

    private final UnitMasterRepository unitMasterRepository;

    private final ProcessMasterRepository processMasterRepository;

    private final ProcessMasterMapper processMasterMapper;

    private final EntityManager entityManager;


    public ProcessMasterServiceImpl(AppMasterRepository appMasterRepository, UnitMasterRepository unitMasterRepository, ProcessMasterRepository processMasterRepository, ProcessMasterMapper processMasterMapper, EntityManager entityManager) {
        this.appMasterRepository = appMasterRepository;
        this.unitMasterRepository = unitMasterRepository;
        this.processMasterRepository = processMasterRepository;
        this.processMasterMapper = processMasterMapper;
        this.entityManager = entityManager;
    }

    @Override
    public ProcessMasterResponse addProcess(ProcessMasterRequest processMasterRequest) {

        processMasterRequest.setIsActive("Y");

        if(!getProcessMaster(processMasterRequest.getProcessName()).isEmpty()){
            log.error("Process Already exists with the given processName");
            throw new ProcessMasterException("Process Already exists with the given processName", HttpStatus.NOT_FOUND);
        }

        ProcessMaster processMaster=processMasterMapper.toEntity(processMasterRequest);
        log.info("ProcessMasterRequest is mapped to ProcessMaster");

        AppMaster appMaster = getAppMaster(processMasterRequest.getAppId());
        log.info("AppMaster was found with the help of appId");

        UnitMaster unitMaster=getUnitMaster(processMasterRequest.getUnitId());
        log.info("UnitMaster was found with the help of unitId");

        processMasterRepository.save(processMaster);
        log.info("ProcessMaster record is saved");

        unitMaster.getProcessMstList().add(processMaster);
        unitMasterRepository.save(unitMaster);
        log.info("ProcessMaster is mapped to UnitMaster");

        appMaster.getProcessMstList().add(processMaster);
        appMasterRepository.save(appMaster);
        log.info("ProcessMaster is mapped to AppMaster");

        return processMasterMapper.toResponse(processMaster,appMaster.getAppId(),unitMaster.getUnitId());

    }

    public AppMaster getAppMaster(long id){
       return appMasterRepository.findByAppIdAndIsActive(id,"Y")
                .orElseThrow( () -> new ProcessMasterException("AppId NotFound", HttpStatus.NOT_FOUND));
    }

    public UnitMaster getUnitMaster(int id){
       return unitMasterRepository.findByUnitIdAndIsActive(id,"Y")
                .orElseThrow( () -> new ProcessMasterException("UnitIdFound", HttpStatus.NOT_FOUND));
    }

    public Optional<ProcessMaster> getProcessMaster(String processName){

        Optional<ProcessMaster> processMasterOptional =processMasterRepository.findByProcessNameAndIsActive(processName,"Y");
        log.info("Query to fetch the list if any existing ProcessName is matching with given processName ");

        return processMasterOptional;
    }

    @Override
    public ProcessMasterResponse updateProcess(ProcessMasterRequest processMasterRequest) {

       Optional<ProcessMaster> processMasterOptional= getProcessMaster(processMasterRequest.getProcessName());

        if(processMasterOptional.isEmpty()){
            log.error("Process Already exists with the given processName");
            throw new ProcessMasterException("Process Already exists with the given processName", HttpStatus.NOT_FOUND);
        }

       AppMaster appMaster = getAppMaster(processMasterRequest.getAppId());
        log.info("AppMaster was found with the help of appId");

       UnitMaster unitMaster=getUnitMaster(processMasterRequest.getUnitId());
        log.info("UnitMaster was found with the help of unitId");

        ProcessMaster mapperProcessMaster=processMasterMapper.toEntity(processMasterRequest);
        log.info("ProcessMasterRequest is mapped to ProcessMaster");

        mapperProcessMaster.setProcessId(processMasterOptional.get().getProcessId());
        processMasterRepository.save(mapperProcessMaster);
        log.info("ProcessMaster record is updated");

        if (!unitMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processMasterOptional.get().getProcessId())) {
            unitMaster.getProcessMstList().add(mapperProcessMaster);
            unitMasterRepository.save(unitMaster);
            log.info("ProcessMaster is mapped to UnitMaster if it is not mapped already");
        }

        if (!appMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processMasterOptional.get().getProcessId())) {
            appMaster.getProcessMstList().add(processMasterOptional.get());
            appMasterRepository.save(appMaster);
            log.info("ProcessMaster is mapped to AppMaster if it is not mapped already");
        }

        return processMasterMapper.toResponse(mapperProcessMaster,appMaster.getAppId(),unitMaster.getUnitId());
    }

    @Override
    public ProcessMasterResponse deleteProcess(int processId) {

        ProcessMaster processMaster =processMasterRepository.findById(processId)
                .orElseThrow(()-> new ProcessMasterException("Process does not exists with the given processName", HttpStatus.NOT_FOUND));
        log.info("Query to fetch the ProcessMaster based on processId");

        processMaster.setIsActive("N");
        log.info("ProcessMaster is setting as InActive");

        return processMasterMapper.toResponse(processMasterRepository.save(processMaster));
    }

    @Override
    public ProcessMasterResponse getProcess(int processId) {

        ProcessMaster processMaster =processMasterRepository.findById(processId)
                .orElseThrow(()-> new ProcessMasterException("Process does not exists with the given processName", HttpStatus.NOT_FOUND));
        log.info("Query to fetch the ProcessMaster based on processId");

        List<UnitMaster> unitMasterList = unitMasterRepository.findAll()
                .stream()
                .filter(unitMaster -> unitMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processId))
                .collect(Collectors.toList());
        log.info("Fetching all UnitMaster list and checking if the given ProcessId is mapped to unitMaster/not ");

        if(unitMasterList.isEmpty()){
            log.error("ProcessMaster with the given processId is not mapped to any UnitMaster");
            throw new ProcessMasterException("ProcessMaster with the given processId is not mapped to UnitMaster",HttpStatus.NOT_FOUND);
        }

        List<AppMaster> appMasterList = appMasterRepository.findAll()
                .stream()
                .filter(appMaster -> appMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processId))
                .collect(Collectors.toList());
        log.info("Fetching all AppMaster list and checking if the given ProcessId is mapped to appMaster/not ");

        if(appMasterList.isEmpty()){
            log.error("ProcessMaster with the given processId is not mapped to any AppMaster");
            throw new ProcessMasterException("ProcessMaster with the given processId is not mapped to AppMaster",HttpStatus.NOT_FOUND);
        }


        return    processMasterMapper.toResponse(processMaster,
                appMasterList.get(0).getAppId(),
                unitMasterList.get(0).getUnitId());

    }

    @Override
    public List<ProcessMasterResponse> getAllProcess(long appId,int unitId) {

        Set<ProcessMaster> appProcessMasterSet =getAppMaster(appId).getProcessMstList()
                .stream().collect(Collectors.toSet());
        log.info("Fetching all ProcessMasterList based on appId in AppMaster  ");

        Set<ProcessMaster> processMasterList = getUnitMaster(unitId).getProcessMstList()
                .stream()
                .filter(appProcessMasterSet::contains)
                .collect(Collectors.toSet());
        log.info("Fetching all ProcessMasterList based on unitId in UnitMaster");

        if(processMasterList.isEmpty()){
            log.error("No Matching records found for the given appId & unitId");
            throw new ProcessMasterException("No Matching records found for the given appId & unitId",HttpStatus.NOT_FOUND);
        }

        return processMasterList.stream()
                .map(processMaster -> processMasterMapper.toResponse(processMaster,appId,unitId)).collect(Collectors.toList());
    }


}
