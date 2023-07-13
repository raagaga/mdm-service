package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.service.PlantMasterService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plant-master")
@Slf4j
public class PlantMasterController {

    @Autowired
    private PlantMasterService plantMasterService;

    @PostMapping("/details")
    public ResponseEntity<String> postPlantMst(){

        ResponseEntity<String> responseEntity= new ResponseEntity<>(plantMasterService.postPlantMst(), HttpStatus.OK);

        return  null;
    }

    @GetMapping("/details")
    public ResponseEntity<PlantMaster> getPlantMst(){
        ResponseEntity<PlantMaster> responseEntity= new ResponseEntity<>(plantMasterService.getPlantMst(), HttpStatus.OK);
        return responseEntity;
    }

}
