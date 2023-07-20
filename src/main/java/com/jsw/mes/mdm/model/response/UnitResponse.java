package com.jsw.mes.mdm.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitResponse
{
    private int plantId;
    private long appId;
    private int unitId;
    private String isActive;
    private String unitName;
    private String unitDescription;
    private Instant createdDt;
    private Long createdBy;
    private Instant modifiedDt;
    private Long modifiedBy;
}
