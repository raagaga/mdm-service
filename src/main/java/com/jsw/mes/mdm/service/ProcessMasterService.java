package com.jsw.mes.mdm.service;

import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.request.ProcessMasterRequest;
import com.jsw.mes.mdm.model.response.ProcessMasterResponse;

import java.util.List;

public interface ProcessMasterService {

    ProcessMasterResponse addProcess(ProcessMasterRequest processMasterRequest);

    ProcessMasterResponse updateProcess(ProcessMasterRequest processMasterRequest);

    ProcessMasterResponse deleteProcess(int processId);

    ProcessMasterResponse getProcess(int processId);

    List<ProcessMasterResponse> getAllProcess(long appId,int unitId);
}
