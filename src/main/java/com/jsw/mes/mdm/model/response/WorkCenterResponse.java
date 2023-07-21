package com.jsw.mes.mdm.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkCenterResponse {
    private int unitId;
    private int processId;
    private int workCenterId;
    private String workCenterName;
    private String workCenterDescription;
    private String isActive;

    private Instant createdDt;

    private Long createdBy ;

    private Instant modifiedDt;;

    private Long modifiedBy;

}
