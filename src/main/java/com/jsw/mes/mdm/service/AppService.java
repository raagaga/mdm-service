package com.jsw.mes.mdm.service;

import com.jsw.mes.mdm.model.request.AppRequest;
import com.jsw.mes.mdm.model.response.AppResponse;

import java.util.List;

public interface AppService
{
    public AppResponse addApp(AppRequest appRequest);
    public AppResponse updateApp(AppRequest appRequest);
    public AppResponse getApp(long appId);
    public List<AppResponse> getAllApp();
    public List<AppResponse> deleteApps(List<Long> appId);
}
