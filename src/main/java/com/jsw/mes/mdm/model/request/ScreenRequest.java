package com.jsw.mes.mdm.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenRequest {

    private int parentId;

    private int screenId;

    private String screenName;

    private String moduleName;


    private String screenType;

    private String isActive;
}
