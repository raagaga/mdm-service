package com.jsw.mes.mdm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PlantMasterException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    private HttpStatus httpStatus;

    public PlantMasterException() {
        super();
    }

    public PlantMasterException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }


    public PlantMasterException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlantMasterException(Throwable cause) {
        super(cause);
    }


    protected PlantMasterException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
