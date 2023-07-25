package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.model.request.ProcessRequest;
import com.jsw.mes.mdm.model.request.ScreenRequest;
import com.jsw.mes.mdm.model.response.ProcessResponse;
import com.jsw.mes.mdm.model.response.Response;
import com.jsw.mes.mdm.model.response.ScreenResponse;
import com.jsw.mes.mdm.service.ScreenService;
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
@RequestMapping("/screen")
@Slf4j
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping
    @Operation(summary = "Adding Process")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Screen added successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid parentId", content = @Content)
            })
    public ResponseEntity<Response<ScreenResponse>> addScreen(@RequestBody ScreenRequest screenRequest) {
        log.info("Adding Screen is started.......");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(screenService.addScreen(screenRequest)));
    }

    @PutMapping
    @Operation(summary = "Update Screen")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Screen updated Successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid parentId provided", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid screen provided", content = @Content)
            })
    public ResponseEntity<Response<ScreenResponse>> updateScreen(@RequestBody ScreenRequest screenRequest) {
        log.info("update Screen is started.......");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(screenService.updateScreen(screenRequest)));
    }

    @DeleteMapping
    @Operation(summary = "Delete Screen")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Screen deleted Successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid screen id provided with the request",
                            content = @Content),
            })
    public ResponseEntity<Response<List<ScreenResponse>>> deleteScreen(@RequestBody List<Integer> screenIdsList) {

        log.info("Delete Screen is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(screenService.deleteScreen(screenIdsList)));
    }

    @GetMapping("/{screenId}")
    @Operation(summary = "Get Screen")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Screen retrieved successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid screen id provided with the request ",
                            content = @Content),
            })
    public ResponseEntity<Response<ScreenResponse>> getScreen(@RequestParam("screenId") int screenId) {
        log.info("Get Screen is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(screenService.getScreen(screenId)));
    }

    @GetMapping
    @Operation(summary = "Get All Screens")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Screens retrieved successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parentId value provided ",
                            content = @Content),
            })
    public ResponseEntity<Response<List<ScreenResponse>>> getAllScreens( @RequestParam("parentId")int parentId) {
        log.info("Get All Screens is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(screenService.getAllScreens(parentId)));
    }


}
