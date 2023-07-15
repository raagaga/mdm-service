package com.jsw.mes.mdm.mapper;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.model.PlantRequest;
import com.jsw.mes.mdm.model.PlantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

@Mapper
public interface PlantMapper {

//    PlantMapper INSTANCE = Mappers.getMapper(PlantMapper.class);


//    @Mapping(source = "plantRequest.createdBy", target = "createdBy")
//    @Mapping(source = "plantRequest.createdDate", target = "createdDate")
//    @Mapping(source = "plantRequest.modifiedBy", target = "modifiedBy")
//    @Mapping(source = "plantRequest.modifiedDate", target = "modifiedDate")
//    @Mapping(source = "plantRequest.isActive", target = "isActive")
    @Mapping(source = "plantRequest.plantName", target = "plantName")
    public PlantMaster mapPlantMaster(PlantRequest plantRequest);

    public PlantResponse mapPlantResponse(PlantMaster plantMaster);

}
