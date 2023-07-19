package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.model.request.PlantRequest;
import com.jsw.mes.mdm.model.request.ProcessMasterRequest;
import com.jsw.mes.mdm.model.response.PlantResponse;
import com.jsw.mes.mdm.model.response.ProcessMasterResponse;
import com.jsw.mes.mdm.model.response.Response;
import com.jsw.mes.mdm.service.ProcessMasterService;
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
@RequestMapping("/process")
@Log4j2
public class ProcessMasterController {

    private final ProcessMasterService processMasterService;

    public ProcessMasterController(ProcessMasterService processMasterService) {
        this.processMasterService = processMasterService;
    }

    @PostMapping
    @Operation(summary = "Adding Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process created successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid AppId", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid UnitId", content = @Content)
            })
    public ResponseEntity<Response<ProcessMasterResponse>> addProcess(@RequestBody ProcessMasterRequest processMasterRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(processMasterService.addProcess(processMasterRequest)));
    }

    @PutMapping
    @Operation(summary = "Update Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process updated Successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid AppId", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid UnitId", content = @Content)
            })
    public ResponseEntity<Response<ProcessMasterResponse>> updateProcess(@RequestBody ProcessMasterRequest processMasterRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(processMasterService.updateProcess(processMasterRequest)));
    }

    @DeleteMapping("/{processId}")
    @Operation(summary = "Delete Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process deleted Successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Invalid Process",
                            content = @Content),
            })
    public ResponseEntity<Response<ProcessMasterResponse>> deleteProcess(@RequestParam("processId") int processId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(processMasterService.deleteProcess(processId)));
    }

    @GetMapping("/{processId}")
    @Operation(summary = "Get Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process retrieved successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Invalid process id provided",
                            content = @Content),
            })
    public ResponseEntity<Response<ProcessMasterResponse>> getProcess(@RequestParam("plantId") int processId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(processMasterService.getProcess(processId)));
    }

    @GetMapping("")
    @Operation(summary = "Get All Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process retrieved successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No records found",
                            content = @Content),
            })
    public ResponseEntity<Response<List<ProcessMasterResponse>>> getAllProcess() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(processMasterService.getAllProcess()));
    }


}
