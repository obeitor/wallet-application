package com.nimisitech.wallet.service.controllers;

import com.nimisitech.wallet.integration.apimodels.WalletCreationRequest;
import com.nimisitech.wallet.integration.apimodels.responses.BalanceResponse;
import com.nimisitech.wallet.integration.apimodels.responses.NameEnquireResponse;
import com.nimisitech.wallet.lib.enums.WalletStatus;
import com.nimisitech.wallet.lib.exceptions.WalletCreationException;
import com.nimisitech.wallet.lib.exceptions.WalletNotFoundException;
import com.nimisitech.wallet.lib.exceptions.WalletTypeException;
import com.nimisitech.wallet.lib.models.Wallet;
import com.nimisitech.wallet.lib.service.WalletService;
import com.nimisitech.wallet.service.services.WalletCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public Wallet createWallet(@RequestBody WalletCreationRequest request) throws WalletTypeException, WalletCreationException {
        return walletService.save(request.getName(),request.getWalletCode());
    }

    @PutMapping("/deactivate/{walletId}")
    public Wallet deactivateWallet(@PathVariable("walletId")String walletId) throws WalletNotFoundException {
        return walletService.save(WalletStatus.DEACTIVATED,walletId);
    }

    @PutMapping("/update/credit-status/{walletId}")
    public Wallet updateCreditStatus(@PathVariable("walletId")String walletId,
                                          @RequestParam(value = "canCredit")Boolean canCredit) throws WalletNotFoundException {
        return walletService.updateCreditStatus(walletId,canCredit);
    }

    @PutMapping("/update/debit-status/{walletId}")
    public Wallet updateDebitStatus(@PathVariable("walletId")String walletId,
                                    @RequestParam(value = "canDebit")Boolean canDebit)throws WalletNotFoundException{
        return walletService.updateDebitStatus(walletId,canDebit);
    }

    @GetMapping("/{walletId}")
    public Wallet getWallet(@PathVariable("walletId")String walletId)throws WalletNotFoundException{
        return walletService.get(walletId);
    }

    @GetMapping
    public Page<Wallet> getAllWallets(
            @RequestParam(value = "page",defaultValue = "0", required = false)Integer page,
            @RequestParam(value = "size",defaultValue = "10", required = false)Integer size
            ){
        return walletService.get(PageRequest.of(page,size));
    }

    @GetMapping("/wallet-type/{type}")
    public Page<Wallet> getWalletsByWalletType(
            @PathVariable(value = "type")String type,
            @RequestParam(value = "page",defaultValue = "0", required = false)Integer page,
            @RequestParam(value = "size",defaultValue = "10", required = false)Integer size
    ) throws WalletTypeException {
        return walletService.get(type,PageRequest.of(page,size));
    }

    @GetMapping("/balance/{walletId}")
    public BalanceResponse getWalletBalance(@PathVariable("walletId")String walletId)throws WalletNotFoundException{
        Wallet wallet = walletService.get(walletId);
        BalanceResponse response = new BalanceResponse();
        response.setAvailableBalanceMinor(wallet.getAvailableBalance());
        response.setBookBalanceMinor(wallet.getBookBalance());
        response.setWalletId(wallet.getWalletKey());
        response.setCurrency(wallet.getCurrencyCode());
        return response;
    }

    @GetMapping("/enquire/{walletId}")
    public NameEnquireResponse getNameEnquiry(@PathVariable("walletId")String walletId) throws WalletNotFoundException {
        Wallet wallet = walletService.get(walletId);
         NameEnquireResponse response = new NameEnquireResponse();
         response.setWalletId(wallet.getWalletKey());
         response.setCurrencyCode(wallet.getCurrencyCode());
         response.setWalletName(wallet.getName());
         return response;
    }
}
