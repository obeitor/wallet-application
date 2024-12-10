package com.nimisitech.wallet.service.controllers;

import com.nimisitech.wallet.lib.exceptions.ApiResponseCode;
import com.nimisitech.wallet.lib.exceptions.LimitException;
import com.nimisitech.wallet.lib.models.WalletLimit;
import com.nimisitech.wallet.lib.service.WalletLimitService;
import com.nimisitech.wallet.service.apimodels.WalletLimitRequest;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/limit")
public class LimitController {

    @Autowired
    private WalletLimitService limitService;

    @PostMapping
    public WalletLimit addLimit(@Validated @RequestBody WalletLimitRequest request) throws LimitException {
        return limitService.saveLimit(request.getWalletLimit());
    }

    @PutMapping(value = "/{code}")
    public WalletLimit updateLimit(@Validated @RequestBody WalletLimitRequest request, @PathVariable(value = "code")String code) throws LimitException {
        WalletLimit limit = limitService.getWallet(code);
        return limitService.saveLimit(request.getWalletLimit(limit),code);
    }

    @GetMapping("/all")
    public List<WalletLimit> getLimits(){
        return limitService.getWallets();
    }

    @GetMapping(value = "/paged")
    public Page<WalletLimit> getLimits(@RequestParam(value = "page", defaultValue = "0", required = false)Integer page,
                                       @RequestParam(value = "size", defaultValue = "10",required = false)Integer size){
        return limitService.getWallets(PageRequest.of(page,size));
    }

    @GetMapping
    public WalletLimit getLimit(@RequestParam(value = "name", required = false)String name,
                                @RequestParam(value = "code", required = false)String code) throws LimitException {
        if(!Strings.isNullOrEmpty(code)){
            return limitService.getWallet(code);
        }
        else if(!Strings.isNullOrEmpty(name)){
            return limitService.getWalletWithName(name);
        }
        throw new LimitException("Limit Code or name must be specified", ApiResponseCode.BAD_REQUEST);
    }
}
