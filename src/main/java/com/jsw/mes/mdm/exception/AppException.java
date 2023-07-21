package com.jsw.mes.mdm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AppException extends RuntimeException
{
    static final long serialVersionUID = -7034897190745766939L;

    private HttpStatus httpStatus;

    public AppException() {
        super();
    }

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }


    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }


    protected AppException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AppException(String s) {
    }
}
