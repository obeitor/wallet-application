package com.nimisitech.wallet.lib.exceptions;

public class WalletNotFoundException extends ApiException{
    public WalletNotFoundException(String key) {
        super(String.format("Wallet with id %s does not exist",key), ApiResponseCode.WALLET_NOT_FOUND);
    }
}
