package com.nimisitech.wallet.lib.exceptions;

public class TransactionNotFoundException extends ApiException{
    public TransactionNotFoundException() {
        super("The transaction was not found", ApiResponseCode.TRANSACTION_NOT_FOUND);
    }
}
