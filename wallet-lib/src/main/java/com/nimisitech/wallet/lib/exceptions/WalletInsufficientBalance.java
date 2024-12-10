package com.nimisitech.wallet.lib.exceptions;

public class WalletInsufficientBalance extends ApiException{
    public WalletInsufficientBalance() {
        super("Insufficient Balance", ApiResponseCode.WALLET_INSUFFICIENT);
    }
}
