package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.model.PlantRequest;
import com.jsw.mes.mdm.model.PlantResponse;
import com.jsw.mes.mdm.service.PlantMasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plant")
@Log4j2
public class PlantMasterController {


    private final PlantMasterService plantMasterService;

    public PlantMasterController(PlantMasterService plantMasterService) {
        this.plantMasterService = plantMasterService;
    }

    @PostMapping
    @Operation(summary = "Adding Plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plant created Successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid Plant Name",
                    content = @Content),
    })
    public ResponseEntity<PlantResponse> addPlant(@RequestBody PlantRequest plantRequest){

        return new ResponseEntity<PlantResponse>(plantMasterService.addPlant(plantRequest), HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update Plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plant updated Successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid Plant Name",
                    content = @Content),
    })
    public ResponseEntity<PlantResponse> updatePlant(@RequestBody PlantRequest plantRequest){

        return new ResponseEntity<PlantResponse>(plantMasterService.updatePlant(plantRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{plantId}")
    @Operation(summary = "Delete Plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plant retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid Plant plantId",
                    content = @Content),
    })
    public ResponseEntity<String> deletePlant(@RequestParam int plantId){

        return new ResponseEntity<String>(plantMasterService.deletePlant(plantId), HttpStatus.OK);
    }

    @GetMapping("/{plantId}")
    @Operation(summary = "Get Plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid Plant plantId",
                    content = @Content),
    })
    public ResponseEntity<PlantResponse> getPlant(@RequestParam int plantId){

        return new ResponseEntity<PlantResponse>(plantMasterService.getPlant(plantId), HttpStatus.OK);
    }


    @GetMapping
    @Operation(summary = "Get All Plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Plants retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No records found",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Invalid Plant plantId",
                    content = @Content),
    })
    public ResponseEntity<List<PlantResponse>> getAllPlants(){

        return new ResponseEntity<List<PlantResponse>>(plantMasterService.getAllPlant(), HttpStatus.OK);
    }


}
