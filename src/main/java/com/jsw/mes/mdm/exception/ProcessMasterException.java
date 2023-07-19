package com.jsw.mes.mdm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ProcessMasterException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    private HttpStatus httpStatus;

    public ProcessMasterException() {
        super();
    }

    public ProcessMasterException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }


    public ProcessMasterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessMasterException(Throwable cause) {
        super(cause);
    }


    protected ProcessMasterException(String message, Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
