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
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
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

        if(!getScreenMaster(screenRequest.getScreenName()).isEmpty()){
            log.error("Screen Already exists with the given ScreenName");
            throw new ScreenException("Screen Already exists with the given ScreenName", HttpStatus.NOT_FOUND);
        }

        log.info("ScreenRequest is mapped to ScreenMaster & saved as saved & screenMaster is mapped to ScreenResponse");

        return screenMapper.toResponse(screenRepository.save(screenMapper.toEntity(screenRequest)));

    }

    @Override
    public ScreenResponse updateScreen(ScreenRequest screenRequest) {

        ScreenMaster screenMasterOptional= screenRepository.findById(screenRequest.getScreenId())
                .orElseThrow( () -> new ProcessException("Screen does not exists with the given screenID", HttpStatus.NOT_FOUND));

        ScreenMaster screenMaster= screenMapper.toEntity(screenRequest);
        log.info("ScreenRequest is mapped to screenMaster");

        screenMaster.setScreenId(screenMasterOptional.getScreenId());
        screenRepository.save(screenMaster);
        log.info("screenMaster record is updated");

        return screenMapper.toResponse(screenMaster);
    }

    @Override
    public ScreenResponse deleteScreen(int screenId) {

        ScreenMaster screenMaster = screenRepository.findById(screenId)
                .orElseThrow(()-> new ScreenException("Screen does not exists with the given screenName", HttpStatus.NOT_FOUND));
        log.info("Query to fetch the ScreenMaster based on screenId");

        screenMaster.setIsActive("N");
        log.info("ProcessMaster is setting as InActive");

        return screenMapper.toResponse(screenRepository.save(screenMaster));
    }

    @Override
    public ScreenResponse getScreen(int screenId) {

        log.info("Query to fetch the ProcessMaster based on processId");

        return   screenMapper.toResponse(screenRepository.findById(screenId)
                .orElseThrow(()-> new ScreenException("Screen does not exists with the given screenId", HttpStatus.NOT_FOUND)));

    }

    @Override
    public List<ScreenResponse> getAllScreens(int parentId) {

        List<ScreenMaster> screenMasters=screenRepository.findByParentIdAndIsActive(parentId,"Y");

        if(screenMasters.isEmpty()){
            throw new ScreenException("Screen records are not found with the given parentId",HttpStatus.NOT_FOUND);
        }

        return screenMasters.stream().map(screenMapper::toResponse).collect(Collectors.toList());
    }

    public Optional<ScreenMaster> getScreenMaster(String screenName){

        log.info("Query to fetch the list if any existing ProcessName is matching with given processName ");
        return screenRepository.findByScreenNameAndIsActive(screenName,"Y");
    }

}
