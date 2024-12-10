package com.nimisitech.wallet.lib.exceptions;

public class InvalidAmountException extends ApiException{
    public InvalidAmountException() {
        super("Invalid Amount", ApiResponseCode.INVALID_AMOUNT);
    }
}
