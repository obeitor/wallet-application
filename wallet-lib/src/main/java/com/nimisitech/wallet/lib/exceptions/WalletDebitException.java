package com.nimisitech.wallet.lib.exceptions;

public class WalletDebitException extends ApiException {
    public WalletDebitException() {
        super("The source wallet cannot be debited", ApiResponseCode.WALLET_CANNOT_DEBIT);
    }
}
