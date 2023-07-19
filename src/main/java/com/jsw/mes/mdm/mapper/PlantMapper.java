package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.response.PlantResponse;
import org.springframework.stereotype.Component;

@Component
public class PlantMapper
    implements EntityMapper<PlantRequest, PlantMaster>, ResponseMapper<PlantMaster, PlantResponse> {

  @Override
  public PlantMaster toEntity(PlantRequest plantRequest) {
    return PlantMaster.builder().plantName(plantRequest.getPlantName()).isActive("Y").build();
  }

  @Override
  public PlantResponse toResponse(PlantMaster plantMaster) {
    return PlantResponse.builder()
        .plantId(plantMaster.getPlantId())
        .plantName(plantMaster.getPlantName())
        .isActive(plantMaster.getIsActive())
        .build();
  }

  @Override
  public PlantResponse toResponse(PlantMaster plantMaster, long primaryId, long secondaryId) {
    return null;
  }

}
