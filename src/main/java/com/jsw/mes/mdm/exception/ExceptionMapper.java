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
  /*  private Exception log(final Exception exception) {
        if (exception instanceof IllegalArgumentException
                || exception instanceof ResourceNotFoundException) {
            log.warn("exception-mapper cause={}, exception={}", exception.getMessage(), exception);
        } else if (exception instanceof RuntimeException) {
            log.error("exception-mapper cause={}, exception={}", exception.getMessage(), exception);
        } else {
            log.debug("exception-mapper cause={}, exception={}", exception.getMessage(), exception);
        }*/

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException exp) {
        return customErrorResponse(new ApiError(exp.getMessage(), HttpStatus.BAD_REQUEST,Instant.now()));

    }


}
