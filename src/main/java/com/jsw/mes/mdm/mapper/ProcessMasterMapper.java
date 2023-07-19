package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.request.ProcessMasterRequest;
import com.jsw.mes.mdm.model.response.PlantResponse;
import com.jsw.mes.mdm.model.response.ProcessMasterResponse;
import org.springframework.stereotype.Component;

@Component
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

        return ProcessMasterResponse.builder()
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

        return ProcessMasterResponse.builder()
                .appId((int) primaryId)
                .unitId((int) secondaryId)
                .processDescription(processMaster.getProcessDescription())
                .processName(processMaster.getProcessName())
                .isActive(processMaster.getIsActive())
                .createdBy(processMaster.getCreatedBy())
                .createdDt(processMaster.getCreatedDt())
                .modifiedBy(processMaster.getModifiedBy())
                .modifiedDt(processMaster.getModifiedDt())
                .build();

    }

    public ProcessMaster toEntity(ProcessMasterRequest source,int id) {

        return ProcessMaster.builder()
                .processId(id)
                .processDescription(source.getProcessDescription())
                .processName(source.getProcessName())
                .isActive(source.getIsActive())
                .build();
    }


}
