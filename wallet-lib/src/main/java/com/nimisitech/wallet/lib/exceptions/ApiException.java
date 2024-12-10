package com.nimisitech.wallet.lib.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends Exception{
    private final ApiResponseCode responseCode;
    private final HttpStatus httpStatus;

    public ApiException(String message, ApiResponseCode responseCode, HttpStatus httpStatus) {
        super(message);
        this.responseCode = responseCode;
        this.httpStatus = httpStatus;
    }

    public ApiException(String message, ApiResponseCode responseCode){
        super(message);
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        this.responseCode = responseCode;
    }

    public ApiException(String message, HttpStatus status, Throwable ex, ApiResponseCode responseCode){
        super(message, ex);
        httpStatus = status;
        this.responseCode = responseCode;
    }
}
