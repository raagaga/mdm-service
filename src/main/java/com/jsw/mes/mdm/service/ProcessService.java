package com.jsw.mes.mdm.service;

import com.jsw.mes.mdm.model.request.ProcessRequest;
import com.jsw.mes.mdm.model.response.ProcessResponse;

import java.util.List;

public interface ProcessService {

    ProcessResponse addProcess(ProcessRequest processRequest);

    ProcessResponse updateProcess(ProcessRequest processRequest);

    List<ProcessResponse>  deleteProcess(List<Integer> processIdsList);

    ProcessResponse getProcess(int processId);

    List<ProcessResponse> getAllProcess(long appId, int unitId);
}
