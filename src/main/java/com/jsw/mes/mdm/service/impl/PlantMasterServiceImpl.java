package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.repository.AppMasterRepository;
import com.jsw.mes.mdm.repository.PlantMasterRepository;
import com.jsw.mes.mdm.repository.UnitMasterRepository;
import com.jsw.mes.mdm.service.PlantMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class PlantMasterServiceImpl implements PlantMasterService {

    @Autowired
    private AppMasterRepository appMasterRepository;

    @Autowired
    private PlantMasterRepository plantMasterRepository;

    @Autowired
    private UnitMasterRepository unitMasterRepository;


    @Override
    public String postPlantMst() {

        PlantMaster plantMst=new PlantMaster();
        plantMst.setPlantName("Vizag");
        plantMst.setStatus("1");
        plantMst.setCreatedBy("jsw_admin");
        plantMst.setCreatedDate(Instant.now());
        plantMst.setModifiedBy("jsw_admin");

        AppMaster appMst=new AppMaster();
         appMst.setAppDescription("apppppp desc");
        appMst.setAppName("CRM");
        appMst.setCreatedBy("jsw_admin");
        appMst.setCreatedDate(Instant.now());
        appMst.setModifiedBy("jsw_admin");
        appMst.setModifiedDate(Instant.now());
        appMst.setStatus("1");

        UnitMaster unitMst= new UnitMaster();
        unitMst.setCreatedBy("jsw_admin");
        unitMst.setCreatedDate(Instant.now());
        unitMst.setModifiedBy("jsw_admin");
        unitMst.setModifiedDate(Instant.now());
        unitMst.setStatus("1");
        unitMst.setUnitDescription("unittttttt desccccccc");
        unitMst.setUnitName("CRM 1");

        plantMst.getUnitMstList().add(unitMst);
        appMst.getUnitMstList().add(unitMst);

        plantMasterRepository.save(plantMst);
        appMasterRepository.save(appMst);
        unitMasterRepository.save(unitMst);

        return null;
    }

    @Override
    public PlantMaster getPlantMst() {

        return plantMasterRepository.findAll().get(0);
    }
}
