package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.model.request.ProcessRequest;
import com.jsw.mes.mdm.model.response.ProcessResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ProcessMapper
        implements EntityMapper<ProcessRequest, ProcessMaster>, ResponseMapper<ProcessMaster, ProcessResponse> {
    @Override
    public ProcessMaster toEntity(ProcessRequest source) {

        return ProcessMaster.builder()
                .processDescription(source.getProcessDescription())
                .processName(source.getProcessName())
                .isActive(source.getIsActive())
                .build();
    }

    @Override
    public ProcessResponse toResponse(ProcessMaster processMaster) {

        log.info("ProcessMaster is mapped to ProcessResponse");

        return ProcessResponse.builder()
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


    public ProcessResponse toResponse(ProcessMaster processMaster, long primaryId, long secondaryId) {

        log.info("ProcessMaster is mapped to ProcessResponse along with appId & unitId");

        return ProcessResponse.builder()
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
