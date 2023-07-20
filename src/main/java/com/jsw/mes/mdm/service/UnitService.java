package com.jsw.mes.mdm.service;

import com.jsw.mes.mdm.model.request.UnitRequest;
import com.jsw.mes.mdm.model.response.UnitResponse;

import java.util.List;

public interface UnitService
{
    public UnitResponse addUnitMaster(UnitRequest unitRequest);

    public UnitResponse updateUnitMaster(UnitRequest unitRequest);

    public UnitResponse getUnitMaster(int unitId);

    public UnitResponse deleteUnitMaster(int UnitId);

    public  List<UnitResponse> getAllUnitMaster(int plantId, long appId);
}