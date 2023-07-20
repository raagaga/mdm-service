package com.jsw.mes.mdm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ScreenException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    private HttpStatus httpStatus;

    public ScreenException() {
        super();
    }

    public ScreenException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }


    public ScreenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScreenException(Throwable cause) {
        super(cause);
    }


    protected ScreenException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
