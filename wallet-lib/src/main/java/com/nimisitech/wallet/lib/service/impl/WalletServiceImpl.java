package com.nimisitech.wallet.lib.service.impl;

import com.nimisitech.wallet.lib.enums.WalletStatus;
import com.nimisitech.wallet.lib.exceptions.*;
import com.nimisitech.wallet.lib.models.Wallet;
import com.nimisitech.wallet.lib.models.WalletType;
import com.nimisitech.wallet.lib.repositories.WalletRepository;
import com.nimisitech.wallet.lib.service.WalletService;
import com.nimisitech.wallet.lib.service.WalletTypeService;
import com.nimisitech.wallet.lib.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository repo;

    @Autowired
    private WalletTypeService walletTypeService;

    private String getNewKey(){
        String newCode = IdGenerator.getUuidBasedId();
        if(repo.existsByWalletKey(newCode)){
            return getNewKey();
        }
        return newCode;
    }

    @Override
    public Wallet save(String name, String walletTypeCode) throws WalletCreationException, WalletTypeException {
        Wallet wallet = new Wallet();
        if(Strings.isNullOrEmpty(name)){
            throw new WalletCreationException("Wallet Name must be alpha numeric with at least 3 characters", ApiResponseCode.BAD_REQUEST);
        }
        if(!Pattern.matches("^[a-zA-Z0-9 ]{3,100}$",name)){
            throw new WalletCreationException("Wallet Name must be alpha numeric with at least 3 characters", ApiResponseCode.BAD_REQUEST);
        }
        wallet.setName(name.toUpperCase());
        wallet.setWalletType(walletTypeService.get(walletTypeCode));
        wallet.setWalletKey(getNewKey());
        return repo.save(wallet);
    }

    @Override
    public Wallet save(String walletKey, boolean canDebit, boolean canCredit) throws WalletNotFoundException {
        Wallet wallet = get(walletKey);
        wallet.setCanCredit(canCredit);
        wallet.setCanDebit(canDebit);
        return repo.save(wallet);
    }

    @Override
    public Wallet updateCreditStatus(String walletKey, boolean canCredit) throws WalletNotFoundException {
        Wallet wallet = get(walletKey);
        wallet.setCanCredit(canCredit);
        return repo.save(wallet);
    }

    @Override
    public Wallet updateDebitStatus(String walletKey, boolean canDebit) throws WalletNotFoundException {
        Wallet wallet = get(walletKey);
        wallet.setCanDebit(canDebit);
        return repo.save(wallet);
    }

    @Override
    public Wallet save(WalletStatus status, String walletKey) throws WalletNotFoundException {
        Wallet wallet = get(walletKey);
        wallet.setStatus(status);
        return repo.save(wallet);
    }

    @Override
    public Wallet get(String walletKey) throws WalletNotFoundException {
        return repo.findByWalletKey(walletKey).orElseThrow(()-> new WalletNotFoundException(walletKey));
    }

    @Override
    public Page<Wallet> get(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Page<Wallet> get(String walletType, Pageable pageable) throws WalletTypeException {
        return repo.findByWalletType(walletTypeService.get(walletType), pageable);
    }

    @Override
    public Wallet updateBalances(Long amount, String key, boolean book, boolean available) throws ApiException {
        validateBalances(amount, key, book, available);
        if(book && available){
            repo.updateBookAndAvailableBalance(amount,key);
        }
        else if(book){
            repo.updateBookBalance(amount,key);
        }
        else if(available){
            repo.updateAvailableBalance(amount, key);
        }
        else{
            throw new ApiException("Incorrect task requested, must update available or book balance",ApiResponseCode.INCORRECT_TASK);
        }
        return get(key);
    }

    @Override
    public Wallet validateBalances(Long amount, String key, boolean book, boolean available) throws ApiException {
        if(amount ==0){
            throw new InvalidAmountException();
        }
        Wallet wallet = get(key);
        if(amount<0 && !wallet.getCanDebit()){
            throw new WalletDebitException();
        }
        if(amount>0 && !wallet.getCanCredit()){
            throw new WalletCreditException();
        }
        if(amount<0){
            if(available && wallet.getWalletType().getLimit().getMinimumBalance() > (wallet.getAvailableBalance()+amount)){
                throw new WalletInsufficientBalance();
            }
            if(book && wallet.getWalletType().getLimit().getMinimumBalance() > (wallet.getBookBalance()+amount)){
                throw new WalletInsufficientBalance();
            }
        }
        else{
            if(available && wallet.getWalletType().getLimit().getMaximumBalance() < (wallet.getAvailableBalance()+amount)){
                throw new WalletAboveMaxBalance();
            }
            if(book && wallet.getWalletType().getLimit().getMaximumBalance() < (wallet.getBookBalance()+amount)){
                throw new WalletAboveMaxBalance();
            }
        }
        return wallet;
    }
}
