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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
            throw new ProcessMasterException("Process Already exists with the given processName", HttpStatus.NOT_FOUND);
        }

        AppMaster appMaster =appMasterRepository.findByAppIdAndIsActive(processMasterRequest.getAppId(),"Y")
                .orElseThrow( () -> new ProcessMasterException("AppId NotFound", HttpStatus.NOT_FOUND));

        UnitMaster unitMaster=unitMasterRepository.findByUnitIdAndIsActive(processMasterRequest.getUnitId(),"Y")
                .orElseThrow( () -> new ProcessMasterException("UnitIdFound", HttpStatus.NOT_FOUND));

        ProcessMaster processMaster1=processMasterMapper.toEntity(processMasterRequest);
        processMasterRepository.save(processMaster1);

        unitMaster.getProcessMstList().add(processMaster1);
        unitMasterRepository.save(unitMaster);

        appMaster.getProcessMstList().add(processMaster1);
        appMasterRepository.save(appMaster);

        return processMasterMapper.toResponse(processMaster1,appMaster.getAppId(),unitMaster.getUnitId());

    }


    @Override
    public ProcessMasterResponse updateProcess(ProcessMasterRequest processMasterRequest) {

       ProcessMaster processMaster =processMasterRepository.findById(processMasterRequest.getProcessId())
               .orElseThrow(()-> new ProcessMasterException("Process does not exists with the given processName", HttpStatus.NOT_FOUND));

       AppMaster appMaster =appMasterRepository.findByAppIdAndIsActive(processMasterRequest.getAppId(),"Y")
                .orElseThrow( () -> new ProcessMasterException("AppId NotFound", HttpStatus.NOT_FOUND));

        UnitMaster unitMaster=unitMasterRepository.findByUnitIdAndIsActive(processMasterRequest.getUnitId(),"Y")
                .orElseThrow( () -> new ProcessMasterException("UnitIdFound", HttpStatus.NOT_FOUND));

        ProcessMaster mapperProcessMaster=processMasterMapper.toEntity(processMasterRequest);
        mapperProcessMaster.setProcessId(processMasterRequest.getProcessId());
        processMasterRepository.save(mapperProcessMaster);

        List unitMasterList=unitMaster.getProcessMstList().stream().filter(
                process -> process.getProcessId() == processMasterRequest.getProcessId()).collect(Collectors.toList());
        if(unitMasterList.isEmpty()){
            unitMaster.getProcessMstList().add(mapperProcessMaster);
            unitMasterRepository.save(unitMaster);
        }

        List appMasterLIst=appMaster.getProcessMstList().stream().filter(
                process -> process.getProcessId() == processMasterRequest.getProcessId()).collect(Collectors.toList());
        if(appMasterLIst.isEmpty()){
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

      List<UnitMaster> unitMasters= unitMasterRepository.findAll();

      List<UnitMaster> unitMasterList=new ArrayList<>();

      for(UnitMaster unitMaster:unitMasters){
         if( unitMaster.getProcessMstList().stream().filter(process -> process.getProcessId() == processId).count() >0 ){
             unitMasterList.add(unitMaster);
         }
      }

        List<AppMaster> appMasters= appMasterRepository.findAll();

        List<AppMaster> appMasterList=new ArrayList<>();

        for(AppMaster appMaster:appMasters){
            if( appMaster.getProcessMstList().stream().filter(process -> process.getProcessId() == processId).count() >0 ){
                appMasterList.add(appMaster);
            }
        }

        return    processMasterMapper.toResponse(processMaster,
                appMasterList.get(0).getAppId(),
                unitMasterList.get(0).getUnitId());

    }

    @Override
    public List<ProcessMasterResponse> getAllProcess() {

        List<ProcessMaster> processMasters=processMasterRepository.findAll();
        if(processMasters.isEmpty()){
            throw new ProcessMasterException("No records Found",HttpStatus.NOT_FOUND);
        }

        List<ProcessMasterResponse> processMasterResponses=new ArrayList<>();

        processMasters.stream().forEach( processMaster -> {
            processMasterResponses.add(getProcess(processMaster.getProcessId()));
        });

        return processMasterResponses;
    }


}
