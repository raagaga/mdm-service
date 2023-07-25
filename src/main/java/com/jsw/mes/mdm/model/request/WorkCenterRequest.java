package com.jsw.mes.mdm.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkCenterRequest {
    private int unitId;
    private int processId;
    private int workCenterId;
    private String workCenterName;
    private String workCenterDescription;
    private String isActive;
}
