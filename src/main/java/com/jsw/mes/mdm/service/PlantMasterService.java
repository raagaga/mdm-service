package com.jsw.mes.mdm.service;


import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.model.PlantRequest;
import com.jsw.mes.mdm.model.PlantResponse;

import java.util.List;


public interface PlantMasterService {

    public PlantResponse addPlant(PlantRequest plantRequest);

    public PlantResponse updatePlant(PlantRequest plantRequest);

    public PlantResponse getPlant(int plantId);

    public String deletePlant(int plantId);

    public  List<PlantResponse> getAllPlant();
}
