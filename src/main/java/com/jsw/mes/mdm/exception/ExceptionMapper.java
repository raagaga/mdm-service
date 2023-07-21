package com.jsw.mes.mdm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;


/**
 * This class is Global_Exception_Handler
 * This class handles each and every exception which has thrown any exception from any corner of the Program
 */
@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler  {

    private ResponseEntity<Object> customErrorResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException exp) {
        return customErrorResponse(new ApiError(exp.getMessage(), HttpStatus.BAD_REQUEST,Instant.now()));
    }

    @ExceptionHandler(ProcessException.class)
    public ResponseEntity<Object> emp(ProcessException exception) {
        return customErrorResponse(new ApiError(exception.getMessage(),exception.getHttpStatus(), Instant.now()));
    }

    @ExceptionHandler(UnitException.class)
    public ResponseEntity<Object> emp(UnitException exp) {
        return customErrorResponse(new ApiError(exp.getMessage(),exp.getHttpStatus(), Instant.now()));
    }

    @ExceptionHandler(ScreenException.class)
    public ResponseEntity<Object> emp(ScreenException exp) {
        return customErrorResponse(new ApiError(exp.getMessage(),exp.getHttpStatus(), Instant.now()));
    }
    @ExceptionHandler(WorkCenterException .class)
    public ResponseEntity<Object> emp(WorkCenterException exception) {
        return customErrorResponse(new ApiError(exception.getMessage(),exception.getHttpStatus(), Instant.now()));

    }
    @ExceptionHandler(DepartmentException .class)
    public ResponseEntity<Object> emp(DepartmentException exception) {
        return customErrorResponse(new ApiError(exception.getMessage(),exception.getHttpStatus(), Instant.now()));

    }



}
