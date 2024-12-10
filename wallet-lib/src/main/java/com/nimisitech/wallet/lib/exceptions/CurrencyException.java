package com.nimisitech.wallet.lib.exceptions;

public class CurrencyException extends ApiException{
    public CurrencyException(String message, ApiResponseCode responseCode) {
        super(message, responseCode);
    }
}
