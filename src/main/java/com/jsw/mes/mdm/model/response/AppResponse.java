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
public class AppResponse
{
    private long appId;
    private String appName;
    private String appDescription;
    private Instant createdDt;
    private Long createdBy;
    private Instant modifiedDt;
    private Long modifiedBy;
}
