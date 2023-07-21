package com.jsw.mes.mdm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class WorkCenterException extends RuntimeException {
    private  HttpStatus httpStatus;

    public WorkCenterException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }


    public WorkCenterException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public WorkCenterException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }


    protected WorkCenterException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
    }

}