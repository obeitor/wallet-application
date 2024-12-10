package com.nimisitech.wallet.lib.service;

import com.nimisitech.wallet.lib.enums.WalletStatus;
import com.nimisitech.wallet.lib.exceptions.*;
import com.nimisitech.wallet.lib.models.Wallet;
import com.nimisitech.wallet.lib.models.WalletType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletService {
    Wallet save(String name,String walletTypeCode) throws WalletCreationException, WalletTypeException;
    Wallet save(String walletKey,boolean canDebit, boolean canCredit) throws WalletNotFoundException;
    Wallet updateCreditStatus(String walletKey, boolean canCredit)throws WalletNotFoundException;
    Wallet updateDebitStatus(String walletKey, boolean canDebit)throws WalletNotFoundException;
    Wallet save(WalletStatus status, String walletKey) throws WalletNotFoundException;
    Wallet get(String walletKey) throws WalletNotFoundException;
    Page<Wallet> get(Pageable pageable);
    Page<Wallet> get(String walletType, Pageable pageable) throws WalletTypeException;
    Wallet updateBalances(Long amount, String key, boolean book, boolean available) throws ApiException;
    Wallet validateBalances(Long amount, String key, boolean book, boolean available)throws ApiException;
}
