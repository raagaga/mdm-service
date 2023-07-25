package com.jsw.mes.mdm.service.impl;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.entity.WorkCenterMaster;
import com.jsw.mes.mdm.exception.WorkCenterException;
import com.jsw.mes.mdm.mapper.WorkCenterMapper;
import com.jsw.mes.mdm.model.request.WorkCenterRequest;
import com.jsw.mes.mdm.model.response.WorkCenterResponse;
import com.jsw.mes.mdm.repository.ProcessRepository;
import com.jsw.mes.mdm.repository.UnitRepository;
import com.jsw.mes.mdm.repository.WorkCenterRepository;
import com.jsw.mes.mdm.service.WorkCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

@Slf4j

public class WorkCenterServiceImpl implements WorkCenterService {


    private final UnitRepository unitRepository;

    private final ProcessRepository processRepository;

    private final WorkCenterRepository workCenterRepository;

    private final WorkCenterMapper workCenterMapper;


    public WorkCenterServiceImpl(WorkCenterRepository workCenterMasterRepository, WorkCenterMapper workCenterMapper,

                                  UnitRepository unitRepository, ProcessRepository processRepository) {

        this.workCenterRepository = workCenterMasterRepository;

        this.workCenterMapper = workCenterMapper;

        this.unitRepository = unitRepository;

        this.processRepository = processRepository;


    }


    @Override

    public WorkCenterResponse addWorkCenter(WorkCenterRequest workCenterRequest) {


        workCenterRequest.setIsActive("Y");


        if(!getWorkCenter(workCenterRequest.getWorkCenterName()).isEmpty()){

            log.error("WorkCenter Already exists with the given workCenterName");

            throw new WorkCenterException("Work-Center Already exists with the given workCenterName: "+workCenterRequest.getWorkCenterName(), HttpStatus.NOT_FOUND);

        }


        WorkCenterMaster workCenterMaster=workCenterMapper.toEntity(workCenterRequest);

        log.info("WorkCenterRequest is mapped to WorkCenterMaster");


        UnitMaster unitMaster=getUnitMaster(workCenterRequest.getUnitId());

        log.info("UnitMaster was found with the help of unitId");


        ProcessMaster processMaster=getProcessMaster(workCenterRequest.getProcessId());

        log.info("ProcessMaster was found with the help of processId");


        workCenterRepository.save(workCenterMaster);

        log.info("WorkCenterMaster record is saved");


        unitMaster.getWorkCenterMstList().add(workCenterMaster);

        unitRepository.save(unitMaster);

        log.info("WorkCenterMaster is mapped to UnitMaster");


        processMaster.getWorkCenterMstList().add(workCenterMaster);

        processRepository.save(processMaster);

        log.info("WorkCenterMaster is mapped to ProcessMaster");


        return workCenterMapper.toResponse(workCenterMaster,unitMaster.getUnitId(),processMaster.getProcessId());


    }


    private ProcessMaster getProcessMaster(int processId) {

        return processRepository.findByProcessIdAndIsActive(processId,"Y")

                .orElseThrow(()-> new WorkCenterException("Process Not Found with the given processId: "+processId,HttpStatus.NOT_FOUND));

    }


    private UnitMaster getUnitMaster(int unitId) {

        return unitRepository.findByUnitIdAndIsActive(unitId,"Y")

                .orElseThrow(() ->new WorkCenterException("Unit Not Found with the given unitId : "+unitId,HttpStatus.NOT_FOUND));

    }


    public Optional<WorkCenterMaster> getWorkCenter(String workCenterName){

        log.info("Query to fetch the list if any existing WorkCenterName is matching with given workCenterName"+workCenterName);

        return workCenterRepository.findByWorkCenterNameAndIsActive(workCenterName,"Y");

    }


    @Override

    public WorkCenterResponse updateWorkCenter(WorkCenterRequest workCenterRequest) {


        WorkCenterMaster workCenterMaster=workCenterRepository.findById(workCenterRequest.getWorkCenterId())

                .orElseThrow(()-> new WorkCenterException("WorkCenter does not exists with the given workCenterId",HttpStatus.NOT_FOUND));


        Optional<WorkCenterMaster> workCenterMasterOptional = getWorkCenter(workCenterRequest.getWorkCenterName());

        log.info("Query to fetch the list if any existing workCenterName is matching with given workCenterName");


        if(!workCenterMasterOptional.isEmpty() && workCenterRequest.getWorkCenterId()!=workCenterMasterOptional.get().getWorkCenterId()){

            log.error("Work-Center Already exists with the given workCenterName");

            throw new WorkCenterException("Work-Center Already exists with the given workCenterName", HttpStatus.NOT_FOUND);

        }


        UnitMaster unitMaster=getUnitMaster(workCenterRequest.getUnitId());

        log.info("UnitMaster was found with the help of unitId");


        ProcessMaster processMaster=getProcessMaster(workCenterRequest.getProcessId());

        log.info("ProcessMaster was found with the help of processId");


        WorkCenterMaster mapperWorkCenterMaster= workCenterMapper.toEntity(workCenterRequest);

        log.info("WorkCenterRequest is mapped To WorkCenterMaster");


        mapperWorkCenterMaster.setWorkCenterId(workCenterMaster.getWorkCenterId());

        workCenterRepository.save(mapperWorkCenterMaster);

        log.info("WorkCenterMaster record is updated");


        if(!unitMaster.getWorkCenterMstList().stream().anyMatch(workCenter ->workCenter.getWorkCenterId()== workCenterMaster.getWorkCenterId())){

            unitMaster.getWorkCenterMstList().add(mapperWorkCenterMaster);

            unitRepository.save(unitMaster);

            log.info("WorkCenterMaster is mapped to UnitMaster if it is not mapped already");

        }


        if(!processMaster.getWorkCenterMstList().stream().anyMatch(workCenter ->workCenter.getWorkCenterId()== workCenterMaster.getWorkCenterId())) {

            processMaster.getWorkCenterMstList().add(mapperWorkCenterMaster);

            processRepository.save(processMaster);

            log.info("WorkCenterMaster is mapped to ProcessMaster if it is not mapped already");

        }


        return workCenterMapper.toResponse(mapperWorkCenterMaster, unitMaster.getUnitId(), processMaster.getProcessId());


    }


    @Override

    public WorkCenterResponse getWorkCenter(int workCenterId) {

        WorkCenterMaster workCenterMaster = getWorkCenterMasterById(workCenterId);


        List<UnitMaster> unitMasterList = unitRepository.findAll()

                .stream()

                .filter(unitMaster -> unitMaster.getWorkCenterMstList().stream().anyMatch(workCenter -> workCenter.getWorkCenterId() == workCenterId))

                .collect(Collectors.toList());

        log.info("Fetching all UnitMaster list and checking if the given WorkCenterId is mapped to unitMaster/not ");


        if(unitMasterList.isEmpty()) {

            log.error("WorkCenterMaster with the given workCenterId is not mapped to any UnitMaster");

            throw new WorkCenterException("WorkCenterMaster with the given workCenterId is not mapped to UnitMaster", HttpStatus.NOT_FOUND);

        }


        List<ProcessMaster> processMasterList =processRepository.findAll()

                .stream()

                .filter(processMaster -> processMaster.getWorkCenterMstList().stream().anyMatch(workCenter -> workCenter.getWorkCenterId() == workCenterId))

                .collect(Collectors.toList());

        log.info("Fetching all ProcessMaster list and checking if the given WorkCenterId is mapped to processMaster/not ");


        if(processMasterList.isEmpty()) {

            log.error("WorkCenterMaster with the given workCenterId is not mapped to any ProcessMaster");

            throw new WorkCenterException("WorkCenterMaster with the given workCenterId is not mapped to ProcessMaster", HttpStatus.NOT_FOUND);

        }


        return workCenterMapper.toResponse(workCenterMaster,unitMasterList.get(0).getUnitId(),processMasterList.get(0).getProcessId());


    }



    @Override

    public List<WorkCenterResponse> deleteWorkCenter(List<Integer> workCenterIdsList) {


        return workCenterIdsList.stream().map(integer -> {

            WorkCenterMaster workCenterMaster = getWorkCenterMasterById(integer);

            workCenterMaster.setIsActive("N");

            log.info("WorkCenterMaster is setting as InActive");


            return workCenterMapper.toResponse(workCenterRepository.save(workCenterMaster));

        }).collect(Collectors.toList());

    }


    @Override

    public List<WorkCenterResponse> getAllWorkCenters(int unitId,int processId) {


        Set<WorkCenterMaster> workCenterMasterSet = getUnitMaster(unitId).getWorkCenterMstList()

                .stream().collect(Collectors.toSet());

        log.info("Fetching all WorkCenterMasterList based on unitId in UnitMaster ");


        Set<WorkCenterMaster> workCenterMastersList =getProcessMaster(processId).getWorkCenterMstList()

                .stream()

                .filter(workCenterMasterSet::contains)

                .collect(Collectors.toSet());

        log.info("Fetching all WorkCenterMasterList based on processId in ProcessMaster");


        if(workCenterMastersList.isEmpty()){

            log.error("No Matching records found for the given unitId & processId");

            throw new WorkCenterException("No Matching records Found for the given unitId and processId",HttpStatus.NOT_FOUND);

        }

        return workCenterMastersList.stream()

                .map(workCenterMaster -> workCenterMapper.toResponse(workCenterMaster,unitId,processId)).collect(Collectors.toList());


    }


    private WorkCenterMaster getWorkCenterMasterById(int workCenterId) {

        log.info("Query to fetch the WorkCenterMaster based on workCenterId");

        return workCenterRepository.findByWorkCenterIdAndIsActive(workCenterId,"Y")

                .orElseThrow(()-> new WorkCenterException("WorkCenter does not exists with the given workCenterId : "+workCenterId, HttpStatus.NOT_FOUND));


    }


}
