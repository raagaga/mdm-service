package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.model.request.ProcessRequest;
import com.jsw.mes.mdm.model.response.ProcessResponse;
import com.jsw.mes.mdm.model.response.Response;
import com.jsw.mes.mdm.service.ProcessService;
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
public class ProcessController {

    private final ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping
    @Operation(summary = "Adding Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process created successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid AppId", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid UnitId", content = @Content)
            })
    public ResponseEntity<Response<ProcessResponse>> addProcess(@RequestBody ProcessRequest processRequest) {
        log.info("Adding Process is started.......");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(processService.addProcess(processRequest)));
    }

    @PutMapping
    @Operation(summary = "Update Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process updated Successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid AppId", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid UnitId", content = @Content)
            })
    public ResponseEntity<Response<ProcessResponse>> updateProcess(@RequestBody ProcessRequest processRequest) {
        log.info("update Process is started.......");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(processService.updateProcess(processRequest)));
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
    public ResponseEntity<Response<ProcessResponse>> deleteProcess(@RequestParam("processId") int processId) {

        log.info("Delete Process is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(processService.deleteProcess(processId)));
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
    public ResponseEntity<Response<ProcessResponse>> getProcess(@RequestParam("plantId") int processId) {
        log.info("Get Process is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(processService.getProcess(processId)));
    }

    @GetMapping()
    @Operation(summary = "Get All Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Process retrieved successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No records found",
                            content = @Content),
            })
    public ResponseEntity<Response<List<ProcessResponse>>> getAllProcess(@RequestParam("appId")long appId, @RequestParam("unitId")int unitId) {
        log.info("Get All Process is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(processService.getAllProcess(appId,unitId)));
    }


}
