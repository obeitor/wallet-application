package com.nimisitech.wallet.lib.service.impl;

import com.nimisitech.wallet.lib.dtos.WalletInsert;
import com.nimisitech.wallet.lib.enums.TransactionStatus;
import com.nimisitech.wallet.lib.enums.TransactionType;
import com.nimisitech.wallet.lib.exceptions.TransactionAlreadyCompletedException;
import com.nimisitech.wallet.lib.exceptions.TransactionNotFoundException;
import com.nimisitech.wallet.lib.models.Wallet;
import com.nimisitech.wallet.lib.models.WalletTransaction;
import com.nimisitech.wallet.lib.repositories.WalletTransactionRepository;
import com.nimisitech.wallet.lib.service.WalletTransactionService;
import com.nimisitech.wallet.lib.utils.DateTimeUtils;
import com.nimisitech.wallet.lib.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
    private WalletTransactionRepository repo;

    @Override
    public WalletTransaction startInsert(WalletInsert insert) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setAmount(insert.getAmount());
        transaction.setData(insert.getData());
        transaction.setExternalReference(insert.getRef());
        transaction.setNarration(insert.getNarration());
        transaction.setReference(generateId());
        transaction.setWalletKey(insert.getWallet().getWalletKey());
        transaction.setTransactionType(insert.isDebit() ? TransactionType.DEBIT : TransactionType.CREDIT);
        transaction.setBalanceBefore(insert.getWallet().getBookBalance());
        int multiplier = insert.isDebit() ? -1 : 1;
        transaction.setBalanceAfter(insert.getWallet().getBookBalance() + (multiplier * insert.getAmount()));
        return repo.save(transaction);
    }

    @Override
    public WalletTransaction completeInsert(String ref) throws TransactionNotFoundException, TransactionAlreadyCompletedException {
        WalletTransaction transaction = queryTransaction(ref);
        if(TransactionStatus.COMPLETED.equals(transaction.getStatus())){
            throw new TransactionAlreadyCompletedException();
        }
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setValueTime(DateTimeUtils.now());
        return repo.save(transaction);
    }

    @Override
    public WalletTransaction cancelTransaction(String ref) throws TransactionAlreadyCompletedException, TransactionNotFoundException {
        WalletTransaction transaction = queryTransaction(ref);
        if(!TransactionStatus.PENDING.equals(transaction.getStatus())){
            throw new TransactionAlreadyCompletedException();
        }
        transaction.setStatus(TransactionStatus.CANCELED);
        return repo.save(transaction);
    }

    @Override
    public WalletTransaction queryTransaction(String ref) throws TransactionNotFoundException {
        return repo.findByReference(ref).orElseThrow(TransactionNotFoundException::new);
    }

    @Override
    public List<WalletTransaction> getAllWithExternalRef(String externalRef) {
        Page<WalletTransaction> page = repo.findByExternalReference(externalRef,PageRequest.of(0,5));
        return page.getContent();
    }

    @Override
    public Page<WalletTransaction> getAll(String walletKey, int page, int size) {
        return repo.findByWalletKey(walletKey, PageRequest.of(page,size, Sort.Direction.DESC,"createdOn"));
    }

    private String generateId(){
        return IdGenerator.idFromDateTime()+IdGenerator.randomNumbers(8);
    }
}
