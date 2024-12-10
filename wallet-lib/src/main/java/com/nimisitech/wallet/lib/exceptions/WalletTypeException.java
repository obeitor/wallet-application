package com.nimisitech.wallet.lib.exceptions;

public class WalletTypeException extends ApiException{
    public WalletTypeException(String message, ApiResponseCode responseCode) {
        super(message, responseCode);
    }
}
