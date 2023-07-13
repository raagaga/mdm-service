package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.entity.UnitMaster;
import com.jsw.mes.mdm.repository.AppMasterRepository;
import com.jsw.mes.mdm.repository.PlantMasterRepository;
import com.jsw.mes.mdm.repository.UnitMasterRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;
public class PlantMasterServiceImplTest {
    @Mock
    AppMasterRepository appMasterRepository;
    @Mock
    PlantMasterRepository plantMasterRepository;
    @Mock
    UnitMasterRepository unitMasterRepository;
    @InjectMocks
    PlantMasterServiceImpl plantMasterServiceImpl;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostPlantMst(){
        String result = plantMasterServiceImpl.postPlantMst();
        Assert.assertEquals(result, "replaceMeWithExpectedResult");
    }

    @Test
    public void testGetPlantMst(){
        PlantMaster result = plantMasterServiceImpl.getPlantMst();
        Assert.assertEquals(result, new PlantMaster(0, "plantName", List.of(new UnitMaster(0, "unitName", "unitDescription", List.of(null), List.of(null)))));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme