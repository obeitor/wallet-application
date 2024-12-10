package com.nimisitech.wallet.lib.service;

import com.nimisitech.wallet.lib.dtos.WalletInsert;
import com.nimisitech.wallet.lib.exceptions.TransactionAlreadyCompletedException;
import com.nimisitech.wallet.lib.exceptions.TransactionNotFoundException;
import com.nimisitech.wallet.lib.models.Wallet;
import com.nimisitech.wallet.lib.models.WalletTransaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WalletTransactionService {
    WalletTransaction startInsert(WalletInsert insert);
    WalletTransaction completeInsert(String ref) throws TransactionNotFoundException, TransactionAlreadyCompletedException;
    WalletTransaction cancelTransaction(String ref) throws TransactionAlreadyCompletedException, TransactionNotFoundException;
    WalletTransaction queryTransaction(String ref) throws TransactionNotFoundException;
    List<WalletTransaction> getAllWithExternalRef(String externalRef);
    Page<WalletTransaction> getAll(String walletKey, int page, int size);
}
