package com.nimisitech.wallet.service.controllers;

import com.nimisitech.wallet.integration.apimodels.WalletTypeRequest;
import com.nimisitech.wallet.lib.exceptions.CurrencyException;
import com.nimisitech.wallet.lib.exceptions.LimitException;
import com.nimisitech.wallet.lib.exceptions.WalletTypeException;
import com.nimisitech.wallet.lib.models.WalletType;
import com.nimisitech.wallet.lib.service.WalletTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/wallet-type")
@RestController
public class WalletTypeController {
    @Autowired
    private WalletTypeService walletTypeService;

    @GetMapping("/all")
    public List<WalletType> get(){
        return walletTypeService.get();
    }

    @GetMapping(value = "/paged")
    public Page<WalletType> get(@RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
                                @RequestParam(value = "size",defaultValue = "10",required = false) Integer size){
        return walletTypeService.get(PageRequest.of(page,size));
    }

    @PostMapping
    public WalletType add(@Validated @RequestBody WalletTypeRequest request) throws WalletTypeException, LimitException, CurrencyException {
        return walletTypeService.save(request.getCurrencyCode(), request.getLimitCode(), request.getName());
    }

    @PutMapping("/{code}")
    public  WalletType update(@Validated @RequestBody WalletTypeRequest request,
                              @PathVariable(value = "code")String code) throws WalletTypeException, LimitException, CurrencyException {
        return walletTypeService.save(request.getCurrencyCode(), request.getLimitCode(), request.getName(),code);
    }

    @GetMapping
    public WalletType get(@RequestParam(value = "code")String code) throws WalletTypeException {
        return walletTypeService.get(code);
    }
}
