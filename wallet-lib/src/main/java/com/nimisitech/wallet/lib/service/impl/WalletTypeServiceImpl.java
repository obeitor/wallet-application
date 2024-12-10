package com.nimisitech.wallet.lib.service.impl;

import com.nimisitech.wallet.lib.exceptions.ApiResponseCode;
import com.nimisitech.wallet.lib.exceptions.CurrencyException;
import com.nimisitech.wallet.lib.exceptions.LimitException;
import com.nimisitech.wallet.lib.exceptions.WalletTypeException;
import com.nimisitech.wallet.lib.models.Currency;
import com.nimisitech.wallet.lib.models.WalletLimit;
import com.nimisitech.wallet.lib.models.WalletType;
import com.nimisitech.wallet.lib.repositories.WalletTypeRepository;
import com.nimisitech.wallet.lib.service.CurrencyService;
import com.nimisitech.wallet.lib.service.WalletLimitService;
import com.nimisitech.wallet.lib.service.WalletTypeService;
import com.nimisitech.wallet.lib.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WalletTypeServiceImpl implements WalletTypeService {

    @Autowired
    private WalletTypeRepository repo;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private WalletLimitService limitService;

    private String getNewWalletCode(){
        String newCode = "W"+ IdGenerator.randomNumbers(3);
        if(repo.existsByCode(newCode)){
            return getNewWalletCode();
        }
        return newCode;
    }

    @Override
    public WalletType save(String currencyCode, String limitCode, String name) throws LimitException, CurrencyException, WalletTypeException {
        WalletLimit limit = limitService.getWallet(limitCode);
        Currency currency = currencyService.getCurrency(currencyCode);
        WalletType type = new WalletType();
        type.setActive(true);
        type.setCode(getNewWalletCode());
        type.setCurrency(currency);
        type.setLimit(limit);
        if(!Pattern.matches("^[a-zA-Z0-9 ]{3,100}$",name)){
            throw new WalletTypeException("Wallet Type name must be alpha numeric with at least 3 characters", ApiResponseCode.BAD_REQUEST);
        }
        type.setName(name);
        return repo.save(type);
    }

    @Override
    public WalletType save(String currencyCode, String limitCode, String name, String code) throws WalletTypeException, CurrencyException, LimitException {
        WalletType type = get(code);
        type.setActive(true);
        if(!Strings.isNullOrEmpty(currencyCode)){
            type.setCurrency(currencyService.getCurrency(currencyCode));
        }
        if(!Strings.isNullOrEmpty(limitCode)){
            type.setLimit(limitService.getWallet(limitCode));
        }
        if(!Strings.isNullOrEmpty(name)) {
            if(!Pattern.matches("^[a-zA-Z0-9 ]{3,100}$",name)){
                throw new WalletTypeException("Wallet Type name must be alpha numeric with at least 3 characters", ApiResponseCode.BAD_REQUEST);
            }
            type.setName(name);
        }
        return repo.save(type);
    }

    @Override
    public WalletType get(String code) throws WalletTypeException {
        return repo.findByCodeAndActiveTrue(code).orElseThrow(()->new WalletTypeException(String.format("Wallet type with code %s not available",code), ApiResponseCode.UNKNOWN_WALLET_TYPE));
    }

    @Override
    public List<WalletType> get() {
        return repo.findAll();
    }

    @Override
    public Page<WalletType> get(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
