package com.nimisitech.wallet.lib.service;

import com.nimisitech.wallet.lib.exceptions.CurrencyException;
import com.nimisitech.wallet.lib.models.Currency;

import java.util.List;

public interface CurrencyService {
    Currency saveCurrency(String code, String name);
    Currency getCurrency(String code) throws CurrencyException;
    List<Currency> getCurrencies();
}
