package com.nimisitech.wallet.integration.exceptions;

public class WalletServiceException extends Exception{
    private String responseCode;
    private boolean responseFromService;

    public WalletServiceException(String message) {
        super(message);
        this.responseCode = "NA";
        this.responseFromService = false;
    }

    public WalletServiceException(String message, String responseCode) {
        super(message);
        this.responseCode = responseCode;
        this.responseFromService = true;
    }
}
