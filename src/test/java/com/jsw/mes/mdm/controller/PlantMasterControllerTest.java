package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.entity.WorkCenterMaster;
import com.jsw.mes.mdm.service.PlantMasterService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;
public class PlantMasterControllerTest {
    @Mock
    PlantMasterService plantMasterService;
    @Mock
    Logger log;
    @InjectMocks
    PlantMasterController plantMasterController;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostPlantMst(){
        when(plantMasterService.postPlantMst()).thenReturn("postPlantMstResponse");

        ResponseEntity<String> result = plantMasterController.postPlantMst();
        Assert.assertEquals(result, null);
    }

    @Test
    public void testGetPlantMst(){
        when(plantMasterService.getPlantMst()).thenReturn(new PlantMaster(0, "plantName", List.of(new UnitMaster(0, "unitName", "unitDescription", List.of(new ProcessMaster(0, "processName", "processDescription", List.of(new WorkCenterMaster(0, "workCenterName", "workCenterDescription")))), List.of(new WorkCenterMaster(0, "workCenterName", "workCenterDescription"))))));

        ResponseEntity<PlantMaster> result = plantMasterController.getPlantMst();
        Assert.assertEquals(result, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme