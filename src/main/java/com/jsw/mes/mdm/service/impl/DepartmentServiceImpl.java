package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.DepartmentMaster;
import com.jsw.mes.mdm.exception.DepartmentException;
import com.jsw.mes.mdm.mapper.DepartmentMapper;
import com.jsw.mes.mdm.model.request.DepartmentRequest;
import com.jsw.mes.mdm.model.response.DepartmentResponse;
import com.jsw.mes.mdm.repository.DepartmentRepository;
import com.jsw.mes.mdm.service.DepartmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentResponse addDepartment(DepartmentRequest departmentRequest) {

        departmentRequest.setIsActive("Y");

        Optional<DepartmentMaster> departmentMasterOptional=departmentRepository.findByDepartmentNameAndIsActive(departmentRequest.getDepartmentName(),"Y");
        log.info("Query to fetch the list if any existing DepartmentName is matching with given departmentName");

        if(!departmentMasterOptional.isEmpty()){
           log.error("Department Already exists with the given departmentName");
            throw new DepartmentException("Department Already exists with the given departmentName", HttpStatus.NOT_FOUND);
       }
        DepartmentMaster departmentMaster=departmentMapper.toEntity(departmentRequest);
        log.info("DepartmentRequest is mapped to DepartmentMaster");

        departmentRepository.save(departmentMaster);
        log.info("DepartmentMaster record is saved");

        return departmentMapper.toResponse(departmentMaster);
    }
    public Optional<DepartmentMaster> getDepartment(String departmentName){
        log.info("Query to fetch the list if any existing DepartmentName is matching with given departmentName");
        return departmentRepository.findByDepartmentNameAndIsActive(departmentName,"Y");
    }

    @Override
    public DepartmentResponse updateDepartment(DepartmentRequest departmentRequest) {
        Optional<DepartmentMaster> departmentMasterOptional= getDepartment(departmentRequest.getDepartmentName());

        if(departmentMasterOptional.isEmpty()){
            log.error("Department does not exists with the given departmentName");
            throw new DepartmentException("Department does not exists with the given departmentName", HttpStatus.NOT_FOUND);
        }
        DepartmentMaster departmentMaster = departmentMapper.toEntity(departmentRequest);
        log.info("DepartmentRequest is mapped to DepartmentMaster");

        departmentMaster.setDepartmentId(departmentMasterOptional.get().getDepartmentId());
        departmentRepository.save(departmentMaster);
        log.info("DepartmentMaster record is updated");

        return departmentMapper.toResponse(departmentMaster);
    }

    @Override
    public DepartmentResponse deleteDepartment(int departmentId) {
        DepartmentMaster departmentMaster= departmentRepository.findById(departmentId)
                .orElseThrow(()-> new DepartmentException("Department does not exist with the given departmentName",HttpStatus.NOT_FOUND));
        log.info("Query to fetch the DepartmentMaster based on departmentId");

//        departmentRepository.deleteById(departmentId);
//        log.info("Deleted Successfully");

        departmentMaster.setIsActive("N");
        log.info("DepartmentMaster is setting as InActive");

         return departmentMapper.toResponse(departmentRepository.save(departmentMaster));

    }

    @Override
    public DepartmentResponse getDepartment(int departmentId) {

        DepartmentMaster departmentMaster=departmentRepository.findById(departmentId)
                .orElseThrow(()-> new DepartmentException("Department does not exist with the given departmentId",HttpStatus.NOT_FOUND));
        log.info("Query to fetch the DepartmentMaster based on departmentId");

        return departmentMapper.toResponse(departmentMaster);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<DepartmentMaster> departmentMasterList=  departmentRepository.findAll();
        log.info("Fetching all DepartmentMasterList");

        return departmentMasterList.stream().map(departmentMaster -> departmentMapper.toResponse(departmentMaster)).collect(Collectors.toList());
    }
}
