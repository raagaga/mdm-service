package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.DepartmentMaster;
import com.jsw.mes.mdm.model.request.DepartmentRequest;
import com.jsw.mes.mdm.model.response.DepartmentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DepartmentMapper implements EntityMapper<DepartmentRequest, DepartmentMaster>,ResponseMapper<DepartmentMaster, DepartmentResponse> {
    @Override
    public DepartmentMaster toEntity(DepartmentRequest departmentRequest) {
        return DepartmentMaster.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .isActive(departmentRequest.getIsActive()).build();
    }

    @Override
    public DepartmentResponse toResponse(DepartmentMaster departmentMaster) {
        log.info("DepartmentMaster is mapped to DepartmentResponse");

        return DepartmentResponse.builder()
                .departmentId(departmentMaster.getDepartmentId())
                .departmentName(departmentMaster.getDepartmentName())
                .isActive(departmentMaster.getIsActive())
                .createdBy(departmentMaster.getCreatedBy())
                .createdDt(departmentMaster.getCreatedDt())
                .modifiedBy(departmentMaster.getModifiedBy())
                .modifiedDt(departmentMaster.getModifiedDt())
                .build();
    }
}
