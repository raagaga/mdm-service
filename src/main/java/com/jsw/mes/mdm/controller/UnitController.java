package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.mapper.UnitMapper;
import com.jsw.mes.mdm.model.request.UnitRequest;
import com.jsw.mes.mdm.model.response.Response;
import com.jsw.mes.mdm.model.response.UnitResponse;
import com.jsw.mes.mdm.repository.UnitRepository;
import com.jsw.mes.mdm.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unit")
@Slf4j
public class UnitController
{
    private final UnitService unitService;
    private final UnitMapper unitMapper;

    private final UnitRepository unitRepository;

    public UnitController(UnitService unitService, UnitMapper unitMapper, UnitRepository unitRepository) {
        this.unitService = unitService;
        this.unitMapper = unitMapper;
        this.unitRepository = unitRepository;
    }

    @PostMapping
    @Operation(summary = "Add Unit")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Unit created Successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid plant id provided", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid application id provided", content = @Content),
            })
    public ResponseEntity<Response<UnitResponse>> addUnit(@RequestBody UnitRequest unitRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of((unitService.addUnitMaster(unitRequest))));

    }

    @PutMapping
    @Operation(summary = "Update Unit")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Unit updated Successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid plant id provided.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid application id provided", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid unit id provided.", content = @Content),
            })
    public ResponseEntity<Response<UnitResponse>> updateUnit(@RequestBody UnitRequest unitRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of((unitService.updateUnitMaster(unitRequest))));
    }

    @DeleteMapping("/{unitId}")
    @Operation(summary = "Delete Unit")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Unit deleted successfully."),
                    @ApiResponse(responseCode = "404",description = "Invalid unit id provided.",content = @Content),
            })
    public ResponseEntity<Response<UnitResponse>> deleteUnit(@RequestParam("unitId") int unitId) {

        return ResponseEntity.ok(
                Response.of((unitService.deleteUnitMaster(unitId))));
    }

    @GetMapping("/{unitId}")
    @Operation(summary = "Get Unit")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Unit retrieved successfully."),
                    @ApiResponse(responseCode = "404",description = "Invalid unit id provided.",content = @Content),
            })
    public ResponseEntity<Response<UnitResponse>> getUnit(@RequestParam("unitId") int unitId) {
        return ResponseEntity.ok(
                Response.of((unitService.getUnitMaster(unitId))));
    }
    @GetMapping("/{plantId},{appId}")
    @Operation(summary = "Get All Unit")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Units retrieved successfully."),
                    @ApiResponse(responseCode = "404", description = "No records found", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid filters value provided. ",content = @Content),
            })
    public ResponseEntity<Response<List<UnitResponse>>> getAllUnits(int plantId, long appId) {
        return ResponseEntity.ok(
                Response.of((unitService.getAllUnitMaster(plantId, appId))));
    }

}

