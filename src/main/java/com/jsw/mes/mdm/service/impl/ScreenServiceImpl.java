package com.jsw.mes.mdm.service.impl;

import com.jsw.mes.mdm.entity.ProcessMaster;
import com.jsw.mes.mdm.entity.ScreenMaster;
import com.jsw.mes.mdm.exception.ProcessException;
import com.jsw.mes.mdm.exception.ScreenException;
import com.jsw.mes.mdm.mapper.ScreenMapper;
import com.jsw.mes.mdm.model.request.ScreenRequest;
import com.jsw.mes.mdm.model.response.ScreenResponse;
import com.jsw.mes.mdm.repository.ScreenRepository;
import com.jsw.mes.mdm.service.ScreenService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;

    private final ScreenMapper screenMapper;

    public ScreenServiceImpl(ScreenRepository screenRepository, ScreenMapper screenMapper) {
        this.screenRepository = screenRepository;
        this.screenMapper = screenMapper;
    }

    @Override
    public ScreenResponse addScreen(ScreenRequest screenRequest) {

        screenRequest.setIsActive("Y");

        if(getScreenMaster(screenRequest.getScreenName()) != null){
            log.error("Screen Already exists with the given ScreenName: "+screenRequest.getScreenName());
            throw new ScreenException("Screen Already exists with the given ScreenName: "+screenRequest.getScreenName(), HttpStatus.NOT_FOUND);
        }

        log.info("ScreenRequest is mapped to ScreenMaster & saved as saved & screenMaster is mapped to ScreenResponse");

        return screenMapper.toResponse(screenRepository.save(screenMapper.toEntity(screenRequest)));

    }


    @Override
    public ScreenResponse updateScreen(ScreenRequest screenRequest) {

        ScreenMaster screenMaster=screenRepository.findById(screenRequest.getScreenId())
                .orElseThrow(() -> new ProcessException("Screen does not exists with the given screenId",HttpStatus.NOT_FOUND) );

        Optional<ScreenMaster> screenMasterOptional=getScreenMaster(screenRequest.getScreenName());

        if(!screenMasterOptional.isEmpty() && screenRequest.getScreenId() != screenMasterOptional.get().getScreenId()){
                log.error("Screen Already exists with the given screenName");
                throw new ProcessException("Screen Already exists with the given screenName", HttpStatus.NOT_FOUND);
        }

        ScreenMaster screenMasterMapper= screenMapper.toEntity(screenRequest);
        log.info("ScreenRequest is mapped to screenMaster");

        screenMasterMapper.setScreenId(screenMaster.getScreenId());
        screenRepository.save(screenMasterMapper);
        log.info("screenMaster record is updated");

        return screenMapper.toResponse(screenMaster);
    }

    @Override
    public List<ScreenResponse> deleteScreen(List<Integer> screenIdsList) {


        return screenIdsList.stream().map(
                integer -> {
                    ScreenMaster screenMaster=getScreenMasterById(integer);
                    screenMaster.setIsActive("N");
                    log.info("ProcessMaster is setting as InActive");
                    return screenMapper.toResponse(screenRepository.save(screenMaster));
                }).collect(Collectors.toList());
    }

    @Override
    public ScreenResponse getScreen(int screenId) {

        log.info("Query to fetch the ProcessMaster based on processId");
        return screenMapper.toResponse(getScreenMasterById(screenId));

    }

    @Override
    public List<ScreenResponse> getAllScreens(int parentId) {

        List<ScreenMaster> screenMasters=screenRepository.findByParentIdAndIsActive(parentId,"Y");

        if(screenMasters == null){
            throw new ScreenException("Screen records are not found with the given parentId",HttpStatus.NOT_FOUND);
        }

        return screenMasters.stream().map(screenMapper::toResponse).collect(Collectors.toList());
    }

    public Optional<ScreenMaster> getScreenMaster(String screenName){

        log.info("Query to fetch the list if any existing ProcessName is matching with given processName ");
        return screenRepository.findByScreenNameAndIsActive(screenName,"Y");
    }


    public ScreenMaster getScreenMasterById(int screenId){
        log.info("Query to fetch the ScreenMaster based on screenId");
        Optional<ScreenMaster> master= screenRepository.findByScreenIdAndIsActive(screenId,"Y");

        if(master == null){
            throw new ScreenException("Screen does not exists with the given screenId: "+screenId, HttpStatus.NOT_FOUND);
        }

        return master.get();

    }
}
