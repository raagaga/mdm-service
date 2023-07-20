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
public class ScreenResponse {

    private int parentId;

    private int screenId;

    private String screenName;

    private String moduleName;

    private String screenType;

    private String isActive;

    private Instant createdDt;

    private Long createdBy ;

    private Instant modifiedDt;;

    private Long modifiedBy;

}
