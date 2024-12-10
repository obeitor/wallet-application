package com.nimisitech.wallet.service.controllers;

import com.nimisitech.wallet.integration.apimodels.CurrencyRequest;
import com.nimisitech.wallet.lib.exceptions.CurrencyException;
import com.nimisitech.wallet.lib.models.Currency;
import com.nimisitech.wallet.lib.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public List<Currency> getCurrencies(){
        return currencyService.getCurrencies();
    }

    @GetMapping(value = "/{code}")
    public Currency getCurrency(@PathVariable(value = "code")String code) throws CurrencyException {
        return currencyService.getCurrency(code);
    }

    @PostMapping
    public Currency saveCurrency(@Validated @RequestBody CurrencyRequest request){
        return currencyService.saveCurrency(request.getCurrencyCode(), request.getName());
    }
}
