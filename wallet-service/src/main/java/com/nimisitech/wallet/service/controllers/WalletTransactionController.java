package com.nimisitech.wallet.service.controllers;

import com.nimisitech.wallet.lib.models.WalletTransaction;
import com.nimisitech.wallet.lib.service.WalletTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
@Slf4j
public class WalletTransactionController {

    @Autowired
    private WalletTransactionService transactionService;

    @GetMapping("/{walletId}")
    public Page<WalletTransaction> getTransactions(
            @PathVariable(value = "walletId")String walletId,
            @RequestParam(required = false,defaultValue = "0", name = "page")Integer page,
            @RequestParam(required = false,defaultValue = "10", name = "size")Integer size){
        return transactionService.getAll(walletId,page,size);
    }

    @GetMapping("/page/{walletId}")
    public List<WalletTransaction> getTransactionsFirstPage(
            @PathVariable(value = "walletId")String walletId,
            @RequestParam(required = false,defaultValue = "0", name = "page")Integer page,
            @RequestParam(required = false,defaultValue = "25", name = "size")Integer size){
        log.info(walletId);
        return transactionService.getAll(walletId,page,size).getContent();
    }
}
