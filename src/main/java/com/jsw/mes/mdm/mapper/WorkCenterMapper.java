package com.jsw.mes.mdm.mapper;


import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.entity.WorkCenterMaster;
import com.jsw.mes.mdm.model.request.WorkCenterRequest;
import com.jsw.mes.mdm.model.response.WorkCenterResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class WorkCenterMapper
        implements EntityMapper<WorkCenterRequest, WorkCenterMaster>,ResponseMapper<WorkCenterMaster, WorkCenterResponse> {

    @Override
    public WorkCenterMaster toEntity(WorkCenterRequest workCenterRequest) {

        return WorkCenterMaster.builder()
                .workCenterName(workCenterRequest.getWorkCenterName())
                .workCenterDescription(workCenterRequest.getWorkCenterDescription())
                .isActive(workCenterRequest.getIsActive())
                .build();

    }

    @Override
    public WorkCenterResponse toResponse(WorkCenterMaster workCenterMaster) {

        log.info("WorkCenterMaster is mapped to WorkCenterResponse");

        return WorkCenterResponse.builder()
                .workCenterId(workCenterMaster.getWorkCenterId())
                .workCenterName(workCenterMaster.getWorkCenterName())
                .workCenterDescription(workCenterMaster.getWorkCenterDescription())
                .isActive(workCenterMaster.getIsActive())
                .createdBy(workCenterMaster.getCreatedBy())
                .createdDt(workCenterMaster.getCreatedDt())
                .modifiedBy(workCenterMaster.getModifiedBy())
                .modifiedDt(workCenterMaster.getModifiedDt())
                .build();
    }


    public WorkCenterResponse toResponse(WorkCenterMaster workCenterMaster, long primaryId, long secondaryId) {

        log.info("WorkCenterMaster is mapped to WorkCenterResponse along with unitId & processId");

        return WorkCenterResponse.builder()
                .workCenterId(workCenterMaster.getWorkCenterId())
                .unitId((int) primaryId)
                .processId((int) secondaryId)
                .workCenterName(workCenterMaster.getWorkCenterName())
                .workCenterDescription(workCenterMaster.getWorkCenterDescription())
                .isActive(workCenterMaster.getIsActive())
               .createdBy(workCenterMaster.getCreatedBy())
                .createdDt(workCenterMaster.getCreatedDt())
                .modifiedBy(workCenterMaster.getModifiedBy())
                .modifiedDt(workCenterMaster.getModifiedDt())
                .build();

    }



    }
