package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.model.request.AppRequest;
import com.jsw.mes.mdm.model.response.AppResponse;
import org.springframework.stereotype.Component;

@Component
public class AppMapper implements EntityMapper<AppRequest, AppMaster>, ResponseMapper<AppMaster, AppResponse>
{
    @Override
    public AppMaster toEntity(AppRequest appRequest) {
        return AppMaster.builder()
                .appName(appRequest.getAppName())
                .appDescription(appRequest.getAppDescription())
                .build();
    }

    @Override
    public AppResponse toResponse(AppMaster appMaster) {
        return AppResponse.builder()
                .appId(appMaster.getAppId())
                .appName(appMaster.getAppName())
                .appDescription(appMaster.getAppDescription())
                .createdDt(appMaster.getCreatedDt())
                .createdBy(appMaster.getCreatedBy())
                .modifiedBy(appMaster.getModifiedBy())
                .modifiedDt(appMaster.getModifiedDt())
                .build();
    }
}

