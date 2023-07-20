package com.jsw.mes.mdm.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitRequest
{
    private int plantId;
    private long appId;
    private int unitId;
    private String unitName;
    private String isActive;
    private String unitDescription;
}