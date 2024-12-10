package com.nimisitech.wallet.service.services;

import com.nimisitech.wallet.lib.exceptions.WalletCreationException;
import com.nimisitech.wallet.lib.exceptions.WalletTypeException;
import com.nimisitech.wallet.lib.models.Wallet;
import com.nimisitech.wallet.lib.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WalletCreationService {
    @Autowired
    private WalletService walletService;

    public Wallet createWallet(String name, String typeCode) throws WalletTypeException, WalletCreationException {
        return walletService.save(name, typeCode);
    }
}
