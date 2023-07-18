package com.jsw.mes.mdm.service;


import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.response.PlantResponse;

import java.util.List;


public interface PlantMasterService {

    public PlantMaster addPlant(PlantRequest plantRequest);

    public PlantMaster updatePlant(PlantRequest plantRequest);

    public PlantMaster getPlant(int plantId);

    public PlantMaster deletePlant(int plantId);

    public  List<PlantMaster> getAllPlant();
}
