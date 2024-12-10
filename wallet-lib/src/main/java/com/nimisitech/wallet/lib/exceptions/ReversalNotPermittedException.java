package com.nimisitech.wallet.lib.exceptions;

public class ReversalNotPermittedException extends ApiException {
    public ReversalNotPermittedException(String message, ApiResponseCode responseCode) {
        super(message, responseCode);
    }
}
