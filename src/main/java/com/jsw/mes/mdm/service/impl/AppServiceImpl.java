package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.AppMaster;
import com.jsw.mes.mdm.exception.AppException;
import com.jsw.mes.mdm.mapper.AppMapper;
import com.jsw.mes.mdm.model.request.AppRequest;
import com.jsw.mes.mdm.model.response.AppResponse;
import com.jsw.mes.mdm.repository.AppRepository;
import com.jsw.mes.mdm.service.AppService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AppServiceImpl implements AppService
{
    private final AppRepository appRepository;
    private final AppMapper appMapper;

    public AppServiceImpl(AppRepository appRepository, AppMapper appMapper) {
        this.appRepository = appRepository;
        this.appMapper = appMapper;
    }

    @Override
    public AppResponse addApp(AppRequest appRequest)
    {
        if(appRequest.getAppName().isEmpty()){
            log.error("Enter App name");
            throw new AppException("Enter App name",HttpStatus.NOT_FOUND);
        }

        AppMaster appMaster = appMapper.toEntity(appRequest);
        if(Objects.nonNull(appRepository.findByAppName(appRequest.getAppName()))){
            throw new AppException("AppName already exists",HttpStatus.NOT_FOUND);
        }
        appRepository.save(appMaster);
        AppResponse appResponse = appMapper.toResponse(appMaster);
        return appResponse;
    }

    @Override
    public AppResponse updateApp(AppRequest appRequest) {
        AppMaster master= appRepository.findById(appRequest.getAppId())
                .orElseThrow( ()-> new AppException("AppId not found",HttpStatus.NOT_FOUND));

        AppMaster appMaster = appMapper.toEntity(appRequest);

        if(Objects.nonNull(appRepository.findByAppName(appRequest.getAppName()))){
            log.error("AppName already exists in DB");
            throw new AppException("AppName already exists",HttpStatus.NOT_FOUND);
        }

        appMaster.setAppId(master.getAppId());
        appRepository.save(appMaster);

        return appMapper.toResponse(appMaster);
    }


    @Override
    public AppResponse getApp(long appId) {
        AppMaster appMaster = appRepository.findById(appId)
                .orElseThrow(()-> new AppException("AppId not found",HttpStatus.NOT_FOUND));

        return appMapper.toResponse(appMaster);
    }

    @Override
    public List<AppResponse> getAllApp() {
        List<AppMaster> appMasterList = appRepository.findAll();
        if(appMasterList.isEmpty()){
            log.error("No App ID's found...");
            throw new AppException("App ID's not found",HttpStatus.NOT_FOUND);
        }
        return appMasterList.stream().map(unitMaster -> getApp(unitMaster.getAppId())).collect(Collectors.toList());
    }

    @Override
    public String deleteApp(long appId) {
        AppMaster appMaster = appRepository.findByAppId(appId);
        if(appMaster == null){
            log.error("App Id Not correct");
            throw new AppException("Invalid AppId provided",HttpStatus.NOT_FOUND);
        }else if(appMaster.getAppId()==appId){
            appRepository.delete(appMaster);
            log.info("App id is deleted");
        }
        return appId + " is deleted";
    }
}
