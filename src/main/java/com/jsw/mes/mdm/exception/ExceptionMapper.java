package com.jsw.mes.mdm.exception;


import lombok.extern.log4j.Log4j;
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

    @ExceptionHandler(ProcessMasterException.class)
    public ResponseEntity<Object> emp(ProcessMasterException exception) {
        return customErrorResponse(new ApiError(exception.getMessage(),exception.getHttpStatus(), Instant.now()));

    }




}
