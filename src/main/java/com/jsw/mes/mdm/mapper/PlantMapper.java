package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.model.PlantRequest;
import com.jsw.mes.mdm.model.PlantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;

@Mapper
public interface PlantMapper {


//    @Mapping(source = "plantRequest.crea", target = "plantName")
//    @Mapping(source = "plantRequest.plantName", target = "plantName")
//    @Mapping(source = "plantRequest.plantName", target = "plantName")
//    @Mapping(source = "plantRequest.plantName", target = "plantName")
//    @Mapping(source = "plantRequest.plantName", target = "plantName")
//    @Mapping(source = "plantRequest.plantName", target = "plantName")
    public PlantMaster mapToPlantMaster(PlantRequest plantRequest);

    public PlantResponse mapToPlantResponse(PlantMaster plantMaster);

    public List<PlantResponse> mapToPlantResponseList(List<PlantMaster> plantMaster);

}
