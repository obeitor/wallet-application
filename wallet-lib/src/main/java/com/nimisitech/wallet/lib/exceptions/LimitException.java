package com.nimisitech.wallet.lib.exceptions;

public class LimitException extends ApiException{
    public LimitException(String message, ApiResponseCode responseCode) {
        super(message, responseCode);
    }
}
