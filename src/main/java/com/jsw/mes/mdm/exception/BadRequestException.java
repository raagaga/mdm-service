package com.jsw.mes.mdm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;


    public BadRequestException() {
        super();
    }


    public BadRequestException(String message) {
        super(message);
    }
}
