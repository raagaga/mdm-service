package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.entity.PlantMaster;
import com.jsw.mes.mdm.mapper.PlantMapper;
import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.response.PlantResponse;
import com.jsw.mes.mdm.model.response.Response;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plant")
@Log4j2
public class PlantMasterController {

  private final PlantMasterService plantMasterService;
  private final PlantMapper plantMapper;

  public PlantMasterController(PlantMasterService plantMasterService, PlantMapper plantMapper) {
    this.plantMasterService = plantMasterService;
    this.plantMapper = plantMapper;
  }

  @PostMapping
  @Operation(summary = "Adding Plant")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Plant created Successfully"),
        @ApiResponse(responseCode = "404", description = "Invalid Plant Name", content = @Content),
      })
  public ResponseEntity<Response<PlantResponse>> addPlant(@RequestBody PlantRequest plantRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(Response.of(plantMapper.toResponse(plantMasterService.addPlant(plantRequest))));
  }

  @GetMapping
  @Operation(summary = "Get All Plants")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "All Plants retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No records found", content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid Plant plantId",
            content = @Content),
      })
  public ResponseEntity<Response<List<PlantResponse>>> getAllPlants() {
    List<PlantMaster> plantMasterList = plantMasterService.getAllPlant();
    return ResponseEntity.ok(
        Response.of(
            plantMasterList.stream().map(plantMapper::toResponse).collect(Collectors.toList())));
  }
  @GetMapping("/{plantId}")
  @Operation(summary = "Get Plant")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Plant deleted successfully"),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Invalid Plant plantId",
                          content = @Content),
          })
  public ResponseEntity<Response<PlantResponse>> getPlant(@RequestParam("plantId") int plantId) {
    return ResponseEntity.ok(
            Response.of(plantMapper.toResponse(plantMasterService.getPlant(plantId))));
  }

  @PutMapping
  @Operation(summary = "Update Plant")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Plant updated Successfully"),
        @ApiResponse(responseCode = "404", description = "Invalid Plant Name", content = @Content),
      })
  public ResponseEntity<Response<PlantResponse>> updatePlant(@RequestBody PlantRequest plantRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(Response.of(plantMapper.toResponse(plantMasterService.updatePlant(plantRequest))));
  }

  @DeleteMapping("/{plantId}")
  @Operation(summary = "Delete Plant")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Plant retrieved Successfully"),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid Plant plantId",
            content = @Content),
      })
  public ResponseEntity<Response<PlantResponse>> deletePlant(@RequestParam("plantId") int plantId) {

    return ResponseEntity.ok(
        Response.of(plantMapper.toResponse(plantMasterService.deletePlant(plantId))));
  }
}
