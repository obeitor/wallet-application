package com.nimisitech.wallet.lib.exceptions;

public class DuplicateTransferReferenceException extends ApiException{
    public DuplicateTransferReferenceException() {
        super("Duplicate Transfer Reference from client", ApiResponseCode.DUPLICATE_TRANSFER_REF);
    }
}
