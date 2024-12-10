package com.nimisitech.wallet.lib.service;

import com.nimisitech.wallet.lib.exceptions.LimitException;
import com.nimisitech.wallet.lib.models.WalletLimit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletLimitService {
    WalletLimit saveLimit(WalletLimit limit);
    WalletLimit saveLimit(WalletLimit limit, String code)throws LimitException;
    WalletLimit getWalletWithName(String name)throws LimitException;
    WalletLimit getWallet(String code)throws LimitException;
    Page<WalletLimit> getWallets(Pageable pageable);
    List<WalletLimit> getWallets();
}
