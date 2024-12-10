package com.nimisitech.wallet.lib.service;

import com.nimisitech.wallet.lib.exceptions.CurrencyException;
import com.nimisitech.wallet.lib.exceptions.LimitException;
import com.nimisitech.wallet.lib.exceptions.WalletTypeException;
import com.nimisitech.wallet.lib.models.WalletType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletTypeService {
    WalletType save(String currencyCode, String limitCode, String name) throws LimitException, CurrencyException, WalletTypeException;
    WalletType save(String currencyCode, String limitCode, String name, String code) throws WalletTypeException, CurrencyException, LimitException;
    WalletType get(String code) throws WalletTypeException;
    List<WalletType> get();
    Page<WalletType> get(Pageable pageable);
}
