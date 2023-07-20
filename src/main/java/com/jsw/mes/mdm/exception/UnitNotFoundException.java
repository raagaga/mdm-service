package com.jsw.mes.mdm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class UnitNotFoundException extends RuntimeException
{
    static final long serialVersionUID = -7034897190745766939L;

    private HttpStatus httpStatus;

    public UnitNotFoundException() {
        super();
    }

    public UnitNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }


    public UnitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnitNotFoundException(Throwable cause) {
        super(cause);
    }


    protected UnitNotFoundException(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnitNotFoundException(String s) {
    }
}
