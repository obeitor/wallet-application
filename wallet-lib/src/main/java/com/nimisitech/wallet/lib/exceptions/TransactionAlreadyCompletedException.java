package com.nimisitech.wallet.lib.exceptions;

public class TransactionAlreadyCompletedException extends ApiException{
    public TransactionAlreadyCompletedException() {
        super("Transaction already completed", ApiResponseCode.TRANSACTION_ALREADY_COMPLTED);
    }
}
