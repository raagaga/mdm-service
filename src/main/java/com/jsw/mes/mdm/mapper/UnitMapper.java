package com.jsw.mes.mdm.mapper;


import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.model.request.UnitRequest;
import com.jsw.mes.mdm.model.response.UnitResponse;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper implements EntityMapper<UnitRequest, UnitMaster>, ResponseMapper<UnitMaster, UnitResponse>
{
    @Override
    public UnitMaster toEntity(UnitRequest unitRequest) {
        return UnitMaster.builder()
                .unitName(unitRequest.getUnitName())
                .unitDescription(unitRequest.getUnitDescription())
                .isActive("Y").build();
    }

    @Override
    public UnitResponse toResponse(UnitMaster unitMaster) {
        return UnitResponse.builder()
                .unitId(unitMaster.getUnitId())
                .unitName(unitMaster.getUnitName())
                .unitDescription(unitMaster.getUnitDescription())
                .isActive(unitMaster.getIsActive())
                .build();
    }

    @Override
    public UnitResponse toResponse(UnitMaster unitMaster, long primaryId, long secondaryId) {
        return UnitResponse.builder()
                .unitId(unitMaster.getUnitId())
                .plantId((int) primaryId)
                .appId(secondaryId)
                .createdDt(unitMaster.getCreatedDt())
                .createdBy(unitMaster.getCreatedBy())
                .modifiedDt(unitMaster.getModifiedDt())
                .modifiedBy(unitMaster.getModifiedBy())
                .unitName(unitMaster.getUnitName())
                .unitDescription(unitMaster.getUnitDescription())
                .isActive(unitMaster.getIsActive())
                .build();
    }
}
