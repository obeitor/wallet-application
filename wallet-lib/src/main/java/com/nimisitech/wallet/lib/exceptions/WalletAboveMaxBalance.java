package com.nimisitech.wallet.lib.exceptions;

public class WalletAboveMaxBalance extends ApiException {
    public WalletAboveMaxBalance() {
        super("Above Maximum Balance", ApiResponseCode.WALLET_ABOVE_MAX_BALANCE);
    }
}
