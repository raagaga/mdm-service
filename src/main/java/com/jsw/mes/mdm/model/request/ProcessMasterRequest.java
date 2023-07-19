package com.jsw.mes.mdm.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessMasterRequest {

    private int appId;

    private int unitId;

    private int processId;

    private String processName;

    private String processDescription;

    private String isActive;

}
