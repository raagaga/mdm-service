package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.ScreenMaster;
import com.jsw.mes.mdm.exception.ScreenException;
import com.jsw.mes.mdm.mapper.ScreenMapper;
import com.jsw.mes.mdm.model.request.ScreenRequest;
import com.jsw.mes.mdm.model.response.ScreenResponse;
import com.jsw.mes.mdm.repository.ScreenRepository;
import org.apache.logging.log4j.Logger;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class ScreenServiceImplTest {
    @Mock
    ScreenRepository screenRepository;
    @Mock
    ScreenMapper screenMapper;
    @Mock
    Logger log;
    @InjectMocks
    ScreenServiceImpl screenServiceImpl;

    ScreenMaster screenMaster=null;

    ScreenResponse screenResponse=null;

    ScreenRequest screenRequest=null;

    List<ScreenMaster> screenMasterList=new ArrayList<ScreenMaster>();

    List<ScreenResponse> screenResponseList=new ArrayList<ScreenResponse>();


    @BeforeMethod
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        screenMaster= new ScreenMaster(0, "screenName", "moduleName", 0, "screenType", "isActive");

        ScreenMaster screenMaster2= new ScreenMaster(1, "dummy", "moduleName", 0, "screenType", "isActive");

        screenResponse=new ScreenResponse(0, 0, "screenName", "moduleName", "screenType", "isActive", Instant.now(), Long.valueOf(0), Instant.now(), Long.valueOf(0));

        ScreenResponse screenResponse2=new ScreenResponse(0, 1, "dummy", "moduleName", "screenType", "isActive", Instant.now(), Long.valueOf(0), Instant.now(), Long.valueOf(0));

        screenRequest=new ScreenRequest(0, 0, "screenName", "moduleName", "screenType", "isActive");

        screenMasterList.addAll(Arrays.asList(screenMaster,screenMaster2));

        screenResponseList.addAll(Arrays.asList(screenResponse,screenResponse2));

    }


    @Test
    public void givenScreenRequest_whenAddScreen_thenReturnScreenResponse() {

        when(screenRepository.findByScreenNameAndIsActive(anyString(), anyString())).thenReturn(null);
        when(screenMapper.toEntity(any())).thenReturn(screenMaster);
        when(screenMapper.toResponse(any())).thenReturn(screenResponse);

        ScreenResponse result = screenServiceImpl.addScreen(screenRequest);
        assertEquals(result,screenResponse);

    }

    @Test
    public void givenScreenRequest_whenAlreadyScreenExists_thenThrowException() {

        when(screenRepository.findByScreenNameAndIsActive(anyString(), anyString())).thenReturn(Optional.ofNullable(screenMaster));

        Assert.expectThrows("Screen Already exists with the given ScreenName: screenName",ScreenException.class,()-> {
                    screenServiceImpl.addScreen(screenRequest);
                });


    }

    @Test
    public void givenScreenRequest_whenUpdateScreen_thenReturnScreenResponse() {
        when(screenRepository.findById(anyInt())).thenReturn(Optional.ofNullable(screenMaster));
        when(screenRepository.findByScreenNameAndIsActive(anyString(), anyString())).thenReturn(Optional.ofNullable(screenMaster));
        when(screenMapper.toEntity(any())).thenReturn(screenMaster);
        when(screenMapper.toResponse(any())).thenReturn(screenResponse);

        ScreenResponse result = screenServiceImpl.updateScreen(screenRequest);
        assertEquals(result,screenResponse);
    }

    @Test
    public void givenScreenRequest_whenUpdateScreen_IdNotFound_thenThrowException() {
        when(screenRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        String message=expectThrows(ScreenException.class,()->screenServiceImpl.updateScreen(screenRequest)).getMessage();
        assertEquals(message,"Screen does not exists with the given screenId");
    }

    @Test
    public void givenScreenRequest_whenUpdateScreen_screenNameAlreadyExists_thenThrowException() {

        ScreenMaster master=new ScreenMaster(1, "screenName", "moduleName", 0, "screenType", "isActive");

        when(screenRepository.findById(anyInt())).thenReturn(Optional.ofNullable(screenMaster));
        when(screenRepository.findByScreenNameAndIsActive(anyString(), anyString())).thenReturn(Optional.of(master));

        String message=expectThrows(Exception.class,()->screenServiceImpl.updateScreen(screenRequest)).getMessage();
        assertEquals(message,"Screen Already exists with the given screenName");
    }

    @Test
    public void givenListOfScreenIds_whenDeleteScreen_thenReturnListOfScreenResponses() {
        when(screenRepository.findByScreenIdAndIsActive(anyInt(), anyString())).thenReturn(Optional.ofNullable(screenMaster));
        when(screenMapper.toResponse(any())).thenReturn(screenResponse);

        List<ScreenResponse> result = screenServiceImpl.deleteScreen(List.of(Integer.valueOf(0)));
        assertEquals(result, List.of(screenResponse));
    }

    @Test
    public void givenListOfScreenIds_whenDeleteScreen_idNotFound_thenThrowException() {
        when(screenRepository.findByScreenIdAndIsActive(anyInt(), anyString())).thenReturn(null);

        String message=expectThrows(Exception.class,()->screenServiceImpl.deleteScreen(List.of(Integer.valueOf(0)))).getMessage();
        assertEquals(message,"Screen does not exists with the given screenId: 0");
    }

    @Test
    public void givenScreenId_whenGetScreen_thenReturnScreenResponse() {
        when(screenRepository.findByScreenIdAndIsActive(anyInt(), anyString())).thenReturn(Optional.ofNullable(screenMaster));
        when(screenMapper.toResponse(any())).thenReturn(screenResponse);

        ScreenResponse result = screenServiceImpl.getScreen(0);
        assertEquals(result, screenResponse);
        verify(screenRepository, times(1)).findByParentIdAndIsActive(0, "Y");
        verify(screenMapper, times(screenResponseList.size())).toResponse(any(ScreenMaster.class));
    }

    @Test
    public void givenScreenId_whenGetScreen_idNotFound_thenThrowException() {
        when(screenRepository.findByScreenIdAndIsActive(anyInt(), anyString())).thenReturn(null);
        String message=expectThrows(Exception.class,()->screenServiceImpl.getScreen(0)).getMessage();
        assertEquals(message,"Screen does not exists with the given screenId: 0");

    }

    @Test
    public void givenParentId_whenGetAllScreen_thenReturnListOfScreenResponses() {

        when(screenRepository.findByParentIdAndIsActive(anyInt(), anyString())).thenReturn(screenMasterList);

        when(screenMapper.toResponse(any(ScreenMaster.class))).thenAnswer(invocation -> {
            ScreenMaster screenMaster = invocation.getArgument(0);
            return new ScreenResponse(screenMaster.getParentId(),screenMaster.getScreenId(),screenMaster.getScreenName(),screenMaster.getModuleName()
            ,screenMaster.getScreenType(),screenMaster.getIsActive(),screenMaster.getCreatedDt(),screenMaster.getCreatedBy(),screenMaster.getModifiedDt(),screenMaster.getModifiedBy());
        });

        List<ScreenResponse> result = screenServiceImpl.getAllScreens(0);

        assertEquals(screenResponseList.size(), result.size());
        assertEquals(screenResponseList, result);
        verify(screenRepository, times(1)).findByParentIdAndIsActive(0, "Y");
        verify(screenMapper, times(screenResponseList.size())).toResponse(any(ScreenMaster.class));

    }

    @Test
    public void givenParentId_whenGetAllScreen_idNotFound_thenThrowException() {

        when(screenRepository.findByParentIdAndIsActive(anyInt(), anyString())).thenReturn(null);

        String message=expectThrows(Exception.class,()->screenServiceImpl.getAllScreens(0)).getMessage();
        assertEquals(message,"Screen records are not found with the given parentId");

    }

    @Test
    public void givenScreenName_whenGetScreenMaster_thenReturnScreenMaster() {
        when(screenRepository.findByScreenNameAndIsActive(anyString(), anyString())).thenReturn(Optional.ofNullable(screenMaster));

        Optional<ScreenMaster> result = screenServiceImpl.getScreenMaster("screenName");
        assertEquals(result.get(), screenMaster);
    }

    @Test
    public void givenScreenId_whenGetScreenMasterById_thenReturnScreenMaster() {
        when(screenRepository.findByScreenIdAndIsActive(anyInt(), anyString())).thenReturn(Optional.ofNullable(screenMaster));

        ScreenMaster result = screenServiceImpl.getScreenMasterById(0);
        assertEquals(result, screenMaster);
    }

    @Test
    public void givenScreenId_whenGetScreenMasterById_idNotFound_thenThrowException() {
        when(screenRepository.findByScreenIdAndIsActive(anyInt(), anyString())).thenReturn(null);

        String message=expectThrows(Exception.class,()->screenServiceImpl.getScreenMasterById(0)).getMessage();
        assertEquals(message,"Screen does not exists with the given screenId: 0");
    }

}
