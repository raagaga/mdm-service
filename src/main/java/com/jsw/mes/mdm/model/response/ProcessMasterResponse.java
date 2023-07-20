package com.jsw.mes.mdm.model.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessMasterResponse {

    private long appId;

    private int unitId;

    private int processId;

    private String processName;

    private String processDescription;

    private String isActive;

    private Instant createdDt;

    private Long createdBy ;

    private Instant modifiedDt;;

    private Long modifiedBy;


}
