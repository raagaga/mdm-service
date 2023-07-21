package com.jsw.mes.mdm.controller;

import com.jsw.mes.mdm.model.request.DepartmentRequest;
import com.jsw.mes.mdm.model.response.DepartmentResponse;
import com.jsw.mes.mdm.model.response.Response;
import com.jsw.mes.mdm.service.DepartmentService;
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
@RequestMapping("/department")
@Log4j2
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @Operation(summary = "Add Department")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "DepartmentMaster created successfully"),
                    @ApiResponse(responseCode = "400", description = "InvalidDepartmentName", content = @Content),
            })
    public ResponseEntity<Response<DepartmentResponse>> addDepartment(@RequestBody DepartmentRequest departmentRequest) {
        log.info("Add Department is started...........");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.of(departmentService.addDepartment(departmentRequest)));
    }

    @PutMapping
    @Operation(summary ="Update Department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DepartmentMaster updated successfully"),
            @ApiResponse(responseCode = "400", description = "InvalidDepartmentId  ",content = @Content)})

    public ResponseEntity<Response<DepartmentResponse>> updateDepartment(@RequestBody DepartmentRequest departmentRequest){
        log.info("Update Department is started.......");
        return  ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(departmentService.updateDepartment(departmentRequest)));
    }
    @DeleteMapping("/{departmentId}")
    @Operation(summary = "Delete Department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "DepartmentMaster deleted successfully. "),
            @ApiResponse(responseCode = "404", description = "InvalidDepartmentId  ", content = @Content)})
    public ResponseEntity<Response<DepartmentResponse>> deleteDepartment(@RequestParam("departmentId") int departmentId) {
        log.info("Delete Department is started.......");
        return ResponseEntity.ok(
                Response.of(departmentService.deleteDepartment(departmentId)));
    }

    @GetMapping("/{departmentId}")
    @Operation(summary = "Get Department")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "DepartmentMaster retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "InvalidDepartmentId",content = @Content)})
    public ResponseEntity<Response<DepartmentResponse>> getDepartment(@RequestParam("departmentId") int departmentId) {
        log.info("Get Department is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(departmentService.getDepartment(departmentId)));
    }

    @GetMapping
    @Operation(summary = "Get All Departments")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "DepartmentMaster retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "NoRecordFound",content = @Content)})
    public ResponseEntity<Response<List<DepartmentResponse>>> getAllDepartments(){
        log.info("Get All Departments is started.......");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.of(departmentService.getAllDepartments()));
    }

}