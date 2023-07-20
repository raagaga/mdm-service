package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.request.ProcessMasterRequest;
import com.jsw.mes.mdm.model.response.PlantResponse;
import com.jsw.mes.mdm.model.response.ProcessMasterResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ProcessMasterMapper
        implements EntityMapper<ProcessMasterRequest, ProcessMaster>, ResponseMapper<ProcessMaster, ProcessMasterResponse> {
    @Override
    public ProcessMaster toEntity(ProcessMasterRequest source) {

        return ProcessMaster.builder()
                .processDescription(source.getProcessDescription())
                .processName(source.getProcessName())
                .isActive(source.getIsActive())
                .build();
    }

    @Override
    public ProcessMasterResponse toResponse(ProcessMaster processMaster) {

        log.info("ProcessMaster is mapped to ProcessMasterResponse");

        return ProcessMasterResponse.builder()
                .processId(processMaster.getProcessId())
                .processDescription(processMaster.getProcessDescription())
                .processName(processMaster.getProcessName())
                .isActive(processMaster.getIsActive())
                .createdBy(processMaster.getCreatedBy())
                .createdDt(processMaster.getCreatedDt())
                .modifiedBy(processMaster.getModifiedBy())
                .modifiedDt(processMaster.getModifiedDt())
                .build();
        
    }

    @Override
    public ProcessMasterResponse toResponse(ProcessMaster processMaster, long primaryId, long secondaryId) {

        log.info("ProcessMaster is mapped to ProcessMasterResponse along with appId & unitId");

        return ProcessMasterResponse.builder()
                .appId(primaryId)
                .unitId((int) secondaryId)
                .processId(processMaster.getProcessId())
                .processDescription(processMaster.getProcessDescription())
                .processName(processMaster.getProcessName())
                .isActive(processMaster.getIsActive())
                .createdBy(processMaster.getCreatedBy())
                .createdDt(processMaster.getCreatedDt())
                .modifiedBy(processMaster.getModifiedBy())
                .modifiedDt(processMaster.getModifiedDt())
                .build();

    }


}
