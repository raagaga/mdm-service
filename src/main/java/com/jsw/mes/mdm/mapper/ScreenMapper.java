package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.ScreenMaster;
import com.jsw.mes.mdm.model.request.ScreenRequest;
import com.jsw.mes.mdm.model.response.ScreenResponse;
import org.springframework.stereotype.Component;

@Component
public class ScreenMapper implements EntityMapper<ScreenRequest, ScreenMaster>,ResponseMapper<ScreenMaster, ScreenResponse>{
    @Override
    public ScreenMaster toEntity(ScreenRequest source) {

        return ScreenMaster.builder()
                .screenName(source.getScreenName())
                .screenType(source.getScreenType())
                .moduleName(source.getModuleName())
                .parentId(source.getParentId())
                .isActive(source.getIsActive())
                .build();
    }

    @Override
    public ScreenResponse toResponse(ScreenMaster screenMaster) {

        return ScreenResponse.builder()
                .screenId(screenMaster.getScreenId())
                .screenName(screenMaster.getScreenName())
                .screenType(screenMaster.getScreenType())
                .moduleName(screenMaster.getModuleName())
                .parentId(screenMaster.getParentId())
                .isActive(screenMaster.getIsActive())
                .createdDt(screenMaster.getCreatedDt())
                .createdBy(screenMaster.getCreatedBy())
                .modifiedBy(screenMaster.getModifiedBy())
                .modifiedDt(screenMaster.getModifiedDt())
                .build();
    }


    public ScreenResponse toResponse(ScreenMaster screenMaster, long primaryId, long secondaryId) {
        return null;
    }
}
