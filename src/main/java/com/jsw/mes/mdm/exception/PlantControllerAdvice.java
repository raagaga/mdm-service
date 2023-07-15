package com.jsw.mes.mdm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;


/**
 * This class is Global_Exception_Handler
 * This class handles each and every exception which has thrown any exception from any corner of the Program
 */
@ControllerAdvice
public class PlantControllerAdvice extends ResponseEntityExceptionHandler  {

    private ResponseEntity<Object> customErrorResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(PlantMasterException.class)
    public ResponseEntity<Object> emp(PlantMasterException exp) {
        return customErrorResponse(new ApiError(exp.getMessage(),exp.getHttpStatus(),Instant.now()));

    }


}
