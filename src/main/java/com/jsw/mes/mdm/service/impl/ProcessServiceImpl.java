package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.exception.ProcessException;
import com.jsw.mes.mdm.mapper.ProcessMapper;
import com.jsw.mes.mdm.model.request.ProcessRequest;
import com.jsw.mes.mdm.model.response.ProcessResponse;
import com.jsw.mes.mdm.repository.AppRepository;
import com.jsw.mes.mdm.repository.ProcessRepository;
import com.jsw.mes.mdm.repository.UnitRepository;
import com.jsw.mes.mdm.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    private final AppRepository appRepository;

    private final UnitRepository unitRepository;

    private final ProcessRepository processRepository;

    private final ProcessMapper processMapper;


    public ProcessServiceImpl(AppRepository appRepository, UnitRepository unitRepository, ProcessRepository processRepository, ProcessMapper processMapper) {
        this.appRepository = appRepository;
        this.unitRepository = unitRepository;
        this.processRepository = processRepository;
        this.processMapper = processMapper;
    }

    @Override
    public ProcessResponse addProcess(ProcessRequest processRequest) {

        processRequest.setIsActive("Y");

        if(!getProcessMaster(processRequest.getProcessName()).isEmpty()){
            log.error("Process Already exists with the given processName: "+processRequest.getProcessName() );
            throw new ProcessException("Process Already exists with the given processName: "+processRequest.getProcessName(), HttpStatus.NOT_FOUND);
        }

        ProcessMaster processMaster= processMapper.toEntity(processRequest);
        log.info("ProcessRequest is mapped to ProcessMaster");

        AppMaster appMaster = getAppMaster(processRequest.getAppId());
        log.info("AppMaster was found with the help of appId");

        UnitMaster unitMaster=getUnitMaster(processRequest.getUnitId());
        log.info("UnitMaster was found with the help of unitId");

        processRepository.save(processMaster);
        log.info("ProcessMaster record is saved");

        unitMaster.getProcessMstList().add(processMaster);
        unitRepository.save(unitMaster);
        log.info("ProcessMaster is mapped to UnitMaster");

        appMaster.getProcessMstList().add(processMaster);
        appRepository.save(appMaster);
        log.info("ProcessMaster is mapped to AppMaster");

        return processMapper.toResponse(processMaster,appMaster.getAppId(),unitMaster.getUnitId());

    }

    @Override
    public ProcessResponse updateProcess(ProcessRequest processRequest) {

        ProcessMaster processMaster=processRepository.findById(processRequest.getProcessId())
                .orElseThrow(() -> new ProcessException("Process does not exists with the given processId",HttpStatus.NOT_FOUND) );

       Optional<ProcessMaster> processMasterValidation=getProcessMaster(processRequest.getProcessName());

        if(!processMasterValidation.isEmpty() && processRequest.getProcessId() != processMasterValidation.get().getProcessId() ){
            log.error("Process Already exists with the given processName");
            throw new ProcessException("Process Already exists with the given processName", HttpStatus.NOT_FOUND);
        }

       AppMaster appMaster = getAppMaster(processRequest.getAppId());
        log.info("AppMaster was found with the help of appId");

       UnitMaster unitMaster=getUnitMaster(processRequest.getUnitId());
        log.info("UnitMaster was found with the help of unitId");

        ProcessMaster mapperProcessMaster= processMapper.toEntity(processRequest);
        log.info("ProcessRequest is mapped to ProcessMaster");

        mapperProcessMaster.setProcessId(processMaster.getProcessId());
        processRepository.save(mapperProcessMaster);
        log.info("ProcessMaster record is updated");

        if (!unitMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processMaster.getProcessId())) {
            unitMaster.getProcessMstList().add(mapperProcessMaster);
            unitRepository.save(unitMaster);
            log.info("ProcessMaster is mapped to UnitMaster if it is not mapped already");
        }

        if (!appMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processMaster.getProcessId())) {
            appMaster.getProcessMstList().add(processMaster);
            appRepository.save(appMaster);
            log.info("ProcessMaster is mapped to AppMaster if it is not mapped already");
        }

        return processMapper.toResponse(mapperProcessMaster,appMaster.getAppId(),unitMaster.getUnitId());
    }

    @Override
    public List<ProcessResponse> deleteProcess(List<Integer> processIdsList) {


        return processIdsList.stream()
                .map(integer -> {
                    ProcessMaster processMaster = getProcessMasterById(integer);
                    processMaster.setIsActive("N");
                    log.info("ProcessMaster is setting as InActive");
                    return processMapper.toResponse(processRepository.save(processMaster));
                })
                .collect(Collectors.toList());

    }

    @Override
    public ProcessResponse getProcess(int processId) {

        ProcessMaster processMaster=getProcessMasterById(processId);

        List<UnitMaster> unitMasterList = unitRepository.findAll()
                .stream()
                .filter(unitMaster -> unitMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processId))
                .collect(Collectors.toList());
        log.info("Fetching all UnitMaster list and checking if the given ProcessId is mapped to unitMaster/not ");

        if(unitMasterList.isEmpty()){
            log.error("ProcessMaster with the given processId is not mapped to any UnitMaster");
            throw new ProcessException("ProcessMaster with the given processId is not mapped to UnitMaster",HttpStatus.NOT_FOUND);
        }

        List<AppMaster> appMasterList = appRepository.findAll()
                .stream()
                .filter(appMaster -> appMaster.getProcessMstList().stream().anyMatch(process -> process.getProcessId() == processId))
                .collect(Collectors.toList());
        log.info("Fetching all AppMaster list and checking if the given ProcessId is mapped to appMaster/not ");

        if(appMasterList.isEmpty()){
            log.error("ProcessMaster with the given processId is not mapped to any AppMaster");
            throw new ProcessException("ProcessMaster with the given processId is not mapped to AppMaster",HttpStatus.NOT_FOUND);
        }


        return    processMapper.toResponse(processMaster,
                appMasterList.get(0).getAppId(),
                unitMasterList.get(0).getUnitId());

    }

    @Override
    public List<ProcessResponse> getAllProcess(long appId, int unitId) {

        Set<ProcessMaster> appProcessMasterSet =getAppMaster(appId).getProcessMstList()
                .stream().collect(Collectors.toSet());
        log.info("Fetching all ProcessMasterList based on appId in AppMaster ");

        Set<ProcessMaster> processMasterList = getUnitMaster(unitId).getProcessMstList()
                .stream()
                .filter(appProcessMasterSet::contains)
                .collect(Collectors.toSet());
        log.info("Fetching all ProcessMasterList based on unitId in UnitMaster");

        if(processMasterList.isEmpty()){
            log.error("No Matching records found for the given appId & unitId");
            throw new ProcessException("No Matching records found for the given appId & unitId",HttpStatus.NOT_FOUND);
        }

        return processMasterList.stream()
                .map(processMaster -> processMapper.toResponse(processMaster,appId,unitId)).collect(Collectors.toList());
    }

    public AppMaster getAppMaster(long id){
        return appRepository.findByAppIdAndIsActive(id,"Y")
                .orElseThrow( () -> new ProcessException("App Not Found with the given appId: "+id, HttpStatus.NOT_FOUND));
    }

    public UnitMaster getUnitMaster(int id){
        return unitRepository.findByUnitIdAndIsActive(id,"Y")
                .orElseThrow( () -> new ProcessException("Unit Not Found with the given unitId : "+ id, HttpStatus.NOT_FOUND));
    }

    public Optional<ProcessMaster> getProcessMaster(String processName){
        log.info("Query to fetch the list if any existing ProcessName is matching with given processName: "+processName);
        return processRepository.findByProcessNameAndIsActive(processName,"Y");
    }

    public ProcessMaster getProcessMasterById(int processId){
        log.info("Query to fetch the ProcessMaster based on processId");
        return processRepository.findByProcessIdAndIsActive(processId,"Y")
                .orElseThrow(()-> new ProcessException("Process does not exists with the given processId : "+processId, HttpStatus.NOT_FOUND));

    }


}
