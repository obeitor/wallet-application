package com.nimisitech.wallet.lib.exceptions;

public class TransferAlreadyReversedException extends ApiException{
    public TransferAlreadyReversedException() {
        super("Transfer has already been reversed", ApiResponseCode.TRANSFER_ALREADY_REVERSED);
    }
}
