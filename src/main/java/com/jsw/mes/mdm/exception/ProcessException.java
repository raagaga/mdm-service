package com.jsw.mes.mdm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ProcessException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    private HttpStatus httpStatus;

    public ProcessException() {
        super();
    }

    public ProcessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }


    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }


    protected ProcessException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
