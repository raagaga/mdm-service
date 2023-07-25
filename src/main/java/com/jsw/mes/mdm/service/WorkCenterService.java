package com.jsw.mes.mdm.service;

import com.jsw.mes.mdm.model.request.WorkCenterRequest;
import com.jsw.mes.mdm.model.response.WorkCenterResponse;

import java.util.List;

public interface WorkCenterService {
   public WorkCenterResponse addWorkCenter(WorkCenterRequest workCenterRequest);

    public WorkCenterResponse getWorkCenter(int workCenterId);

    public WorkCenterResponse updateWorkCenter(WorkCenterRequest workCenterRequest);

   public List<WorkCenterResponse> deleteWorkCenter(List<Integer> workCenterIdsList);

    public List<WorkCenterResponse> getAllWorkCenters( int unitId,int processId);
}
