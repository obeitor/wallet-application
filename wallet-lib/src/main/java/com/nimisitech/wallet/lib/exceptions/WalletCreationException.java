package com.nimisitech.wallet.lib.exceptions;

public class WalletCreationException extends ApiException {
    public WalletCreationException(String message, ApiResponseCode responseCode) {
        super(message, responseCode);
    }
}
