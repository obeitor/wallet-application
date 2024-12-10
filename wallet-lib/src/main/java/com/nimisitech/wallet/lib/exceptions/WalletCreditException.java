package com.nimisitech.wallet.lib.exceptions;

public class WalletCreditException extends ApiException{
    public WalletCreditException() {
        super("The destination wallet cannot be credited", ApiResponseCode.WALLET_CANNOT_CREDIT);
    }
}
