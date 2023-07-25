package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.mapper.AppMapper;
import com.jsw.mes.mdm.model.request.AppRequest;
import com.jsw.mes.mdm.model.response.AppResponse;
import com.jsw.mes.mdm.model.response.Response;
import com.jsw.mes.mdm.repository.AppRepository;
import com.jsw.mes.mdm.service.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master")
@Slf4j
public class AppController
{
    private final AppRepository appRepository;
    private final AppService appService;
    private final AppMapper appMapper;

    public AppController(AppRepository appRepository, AppService appService, AppMapper appMapper) {
        this.appRepository = appRepository;
        this.appService = appService;
        this.appMapper = appMapper;
    }

    @PostMapping
    @Operation(summary = "Add App")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "App created Successfully"),
            })
    public ResponseEntity<Response<AppResponse>> addApp(@RequestBody AppRequest appRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of((appService.addApp(appRequest))));
    }

    @PutMapping
    @Operation(summary = "Edit App")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "App created Successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid app id provided", content = @Content),
            })
    public ResponseEntity<Response<AppResponse>> editApp(@RequestBody AppRequest appRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of((appService.updateApp(appRequest))));
    }

    @GetMapping("/{appId}")
    @Operation(summary = "Get App")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "App retrieved successfully."),
                    @ApiResponse(responseCode = "404",description = "Invalid app id provided.",content = @Content),
            })
    public ResponseEntity<Response<AppResponse>> getApp(@RequestParam("appId") int appId) {
        return ResponseEntity.ok(
                Response.of((appService.getApp(appId))));
    }


    @GetMapping
    @Operation(summary = "Get All App")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Units retrieved successfully."),
                    @ApiResponse(responseCode = "404", description = "No records found", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Invalid filters value provided. ",content = @Content),
            })
    public ResponseEntity<Response<List<AppResponse>>> getAllUnits() {
        return ResponseEntity.ok(
                Response.of((appService.getAllApp())));
    }

    @DeleteMapping("/{appId}")
    @Operation(summary = "Delete App")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "App deleted successfully."),
                    @ApiResponse(responseCode = "404",description = "Invalid app id provided.",content = @Content),
            })
    public ResponseEntity<Response<String>> deleteApp(@RequestParam("appId") int appId) {

        return ResponseEntity.ok(
                Response.of((appService.deleteApp(appId))));
    }
}
