package com.nimisitech.wallet.lib.exceptions;

public class TransferRequestNotFoundException extends ApiException{
    public TransferRequestNotFoundException() {
        super("Transfer request not found", ApiResponseCode.TRANSFER_NOT_FOUND);
    }
}
