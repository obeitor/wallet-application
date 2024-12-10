package com.nimisitech.wallet.lib.service.impl;

import com.nimisitech.wallet.lib.exceptions.ApiResponseCode;
import com.nimisitech.wallet.lib.exceptions.CurrencyException;
import com.nimisitech.wallet.lib.models.Currency;
import com.nimisitech.wallet.lib.repositories.CurrencyRepository;
import com.nimisitech.wallet.lib.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyRepository repo;

    @Override
    public Currency saveCurrency(String code, String name) {
        Currency currency = repo.findByCode(code).orElse(new Currency());
        currency.setCode(code.toUpperCase());
        currency.setName(name);
        return repo.save(currency);
    }

    @Override
    public Currency getCurrency(String code) throws CurrencyException {
        return repo.findByCode(code).orElseThrow(() -> new CurrencyException(String.format("Currency code [%s] is not recognized",code), ApiResponseCode.UNKNOWN_CURRENCY));
    }

    @Override
    public List<Currency> getCurrencies() {
        return repo.findAllByOrderByCodeAsc();
    }
}
