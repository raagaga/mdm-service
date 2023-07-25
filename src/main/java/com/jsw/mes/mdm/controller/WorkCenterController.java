package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.exception.WorkCenterException;
import com.jsw.mes.mdm.model.request.WorkCenterRequest;
import com.jsw.mes.mdm.model.response.Response;
import com.jsw.mes.mdm.model.response.WorkCenterResponse;
import com.jsw.mes.mdm.service.WorkCenterService;
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
@RequestMapping("/work-center")
@Slf4j
public class WorkCenterController {

    private final WorkCenterService workCenterService;


    public WorkCenterController(WorkCenterService workCenterMasterService) {this.workCenterService = workCenterMasterService;}

    @PostMapping
    @Operation(summary = "Add Work-Center")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Work-center created successfully"),
                    @ApiResponse(responseCode = "404", description = "InvalidUnitId",content  = @Content),
                    @ApiResponse(responseCode = "404", description = "InvalidProcessId", content = @Content),
            })
    public ResponseEntity<Response<WorkCenterResponse>> addWorkCenter(@RequestBody WorkCenterRequest workCenterRequest) {
        log.info("Add WorkCenter is started.......");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(workCenterService.addWorkCenter(workCenterRequest)));
    }

    @GetMapping
    @Operation(summary = "Get All Work-Center")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Work-center retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "No records found", content = @Content),
                    @ApiResponse(responseCode = "400", description = "InvalidFilterValue ", content = @Content),
            })
    public ResponseEntity<Response<List<WorkCenterResponse>>> getAllWorkCenters(@RequestParam("unitId")int unitId,@RequestParam("processId")int processId)  {
        log.info("Get All WorkCenter is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(workCenterService.getAllWorkCenters(unitId, processId)));
    }
    @GetMapping("/{workCenterId}")
    @Operation(summary = "Get Work-Center")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Work-center retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "InvalidWorkCenterId ",content = @Content),
            })
    public ResponseEntity<Response<WorkCenterResponse>> getWorkCenter(@RequestParam("workCenterId") int workCenterId)  {
        log.info("Get WorkCenter is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(workCenterService.getWorkCenter(workCenterId)));
    }

    @PutMapping
    @Operation(summary ="Update Work-Center")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work-center updated successfully"),
            @ApiResponse(responseCode = "404", description = "InvalidWorkCenterId ",content = @Content),
            @ApiResponse(responseCode = "404", description = "InvalidUnitId",content = @Content),
            @ApiResponse(responseCode = "404", description = "InvalidProcessId", content = @Content),
            })
    public ResponseEntity<Response<WorkCenterResponse>> updateWorkCenter(@RequestBody WorkCenterRequest workCenterRequest) {
        log.info("update WorkCenter is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(workCenterService.updateWorkCenter(workCenterRequest)));
    }

    @DeleteMapping("/{workCenterId}")
    @Operation(summary = "Delete Work-Center")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Work-center deleted successfully"),
            @ApiResponse(responseCode = "404", description = "InvalidWorkCenterId ", content = @Content),
            })
    public ResponseEntity<Response<WorkCenterResponse>> deleteWorkCenter(@RequestParam("workCenterId") int workCenterId) {
        log.info("delete WorkCenter is started.......");
        return ResponseEntity.ok(
                Response.of(workCenterService.deleteWorkCenter(workCenterId)));
    }
}

