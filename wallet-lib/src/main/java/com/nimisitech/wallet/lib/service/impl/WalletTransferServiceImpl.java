package com.nimisitech.wallet.lib.service.impl;

import com.nimisitech.wallet.lib.dtos.WalletInsert;
import com.nimisitech.wallet.lib.dtos.WalletTransferServiceRequest;
import com.nimisitech.wallet.lib.enums.TransferStatus;
import com.nimisitech.wallet.lib.exceptions.*;
import com.nimisitech.wallet.lib.models.Wallet;
import com.nimisitech.wallet.lib.models.WalletTransaction;
import com.nimisitech.wallet.lib.models.WalletTransfer;
import com.nimisitech.wallet.lib.repositories.WalletTransferRepository;
import com.nimisitech.wallet.lib.service.WalletService;
import com.nimisitech.wallet.lib.service.WalletTransactionService;
import com.nimisitech.wallet.lib.service.WalletTransferService;
import com.nimisitech.wallet.lib.utils.DateTimeUtils;
import com.nimisitech.wallet.lib.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class WalletTransferServiceImpl implements WalletTransferService {

    @Autowired
    private WalletTransferRepository repo;

    @Autowired
    private WalletTransactionService transactionService;

    @Autowired
    private WalletService walletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletTransfer performFullTransfer(WalletTransferServiceRequest request) throws ApiException {
        Wallet debitWallet = walletService.validateBalances(request.getDebitAmount(),request.getDebitWalletKey(),true,true);
        Wallet creditWallet = walletService.validateBalances(request.getAmount(),request.getCreditWalletKey(),true,true);
        WalletTransfer transfer = createWalletTransfer(request);
        transfer.setCreditWallet(creditWallet.getWalletKey());
        transfer.setDebitWallet(debitWallet.getWalletKey());
        WalletTransaction debitTransaction = transactionService.startInsert(WalletInsert
                .builder()
                .amount(request.getAmount())
                .data(request.getAdditionalData())
                .wallet(debitWallet)
                .isDebit(true)
                .narration(request.getNarration())
                .ref(transfer.getTransferRefId())
                .build());
        WalletTransaction creditTransaction = transactionService.startInsert(WalletInsert
                .builder()
                .wallet(creditWallet)
                .isDebit(false)
                .ref(transfer.getTransferRefId())
                .narration(request.getNarration())
                .data(request.getAdditionalData())
                .amount(request.getAmount())
                .build());
        transfer.setCreditRef(creditTransaction.getReference());
        transfer.setDebitRef(debitTransaction.getReference());
        transactionService.completeInsert(transfer.getDebitRef());
        transactionService.completeInsert(transfer.getCreditRef());
        walletService.updateBalances(request.getDebitAmount(),request.getDebitWalletKey(),true,true);
        walletService.updateBalances(request.getAmount(),request.getCreditWalletKey(),true,true);
        transfer.setCreditStatus(TransferStatus.COMPLETE);
        transfer.setDebitStatus(TransferStatus.COMPLETE);
        transfer.setCompletedAt(DateTimeUtils.now());
        return repo.save(transfer);
    }

    @Override
    public WalletTransfer performPartialTransfer(WalletTransferServiceRequest request)throws ApiException {
        Wallet debitWallet = walletService.validateBalances(request.getDebitAmount(),request.getDebitWalletKey(),true,true);
        Wallet creditWallet = walletService.validateBalances(request.getAmount(),request.getCreditWalletKey(),true,true);
        WalletTransfer transfer = createWalletTransfer(request);
        transfer.setCreditWallet(creditWallet.getWalletKey());
        transfer.setDebitWallet(debitWallet.getWalletKey());
        WalletTransaction debitTransaction = transactionService.startInsert(WalletInsert
                .builder()
                .amount(request.getAmount())
                .wallet(debitWallet)
                .isDebit(true)
                .narration(request.getNarration())
                .ref(transfer.getTransferRefId())
                .build());
        WalletTransaction creditTransaction = transactionService.startInsert(WalletInsert
                .builder()
                .wallet(creditWallet)
                .isDebit(false)
                .ref(transfer.getTransferRefId())
                .narration(request.getNarration())
                .amount(request.getAmount())
                .build());
        walletService.updateBalances(request.getDebitAmount(),request.getDebitWalletKey(),false,true);
        walletService.updateBalances(request.getAmount(),request.getCreditWalletKey(),true,false);
        transfer.setCreditRef(creditTransaction.getReference());
        transfer.setDebitRef(debitTransaction.getReference());
        return repo.save(transfer);
    }

    @Override
    public WalletTransfer completeTransfer(String transferRef) throws ApiException{
        WalletTransfer transfer = getTransfer(transferRef);
        incomplete(transfer);
        walletService.validateBalances(transfer.getDebitAmount(),transfer.getDebitWallet(),true,false);
        walletService.validateBalances(transfer.getAmount(),transfer.getCreditWallet(),false,true);
        transactionService.completeInsert(transfer.getDebitRef());
        transactionService.completeInsert(transfer.getCreditRef());
        walletService.updateBalances(transfer.getDebitAmount(),transfer.getDebitWallet(),true,false);
        walletService.updateBalances(transfer.getAmount(),transfer.getCreditWallet(),false,true);
        transfer.setCreditStatus(TransferStatus.COMPLETE);
        transfer.setDebitStatus(TransferStatus.COMPLETE);
        transfer.setCompletedAt(LocalDateTime.now());
        return repo.save(transfer);
    }

    private void incomplete(WalletTransfer walletTransfer)throws ApiException{
        if(TransferStatus.COMPLETE.equals(walletTransfer.getCreditStatus()) && TransferStatus.COMPLETE.equals(walletTransfer.getDebitStatus())){
            throw new ApiException("Transaction already completed", ApiResponseCode.TRANSACTION_ALREADY_COMPLTED);
        }
        if(TransferStatus.REVERSED.equals(walletTransfer.getDebitStatus()) || TransferStatus.REVERSED.equals(walletTransfer.getCreditStatus())){
            throw new TransferAlreadyReversedException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletTransfer performReversal(String transferRef) throws ApiException {
        WalletTransfer transfer = getTransfer(transferRef);
        canDoReversal(transfer);
        return doReversal(transfer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletTransfer performReversal(String requestRef, String clientId) throws ApiException {
        WalletTransfer transfer = getTransfer(requestRef, clientId);
        canDoReversal(transfer);
        return doReversal(transfer);
    }

    private void canDoReversal(WalletTransfer walletTransfer) throws ApiException {
        if(TransferStatus.COMPLETE.equals(walletTransfer.getCreditStatus()) && TransferStatus.COMPLETE.equals(walletTransfer.getDebitStatus())){
            return;
        }
        if(TransferStatus.REVERSED.equals(walletTransfer.getDebitStatus()) || TransferStatus.REVERSED.equals(walletTransfer.getCreditStatus())){
            throw new TransferAlreadyReversedException();
        }

        if(TransferStatus.PENDING.equals(walletTransfer.getDebitStatus())){
            transactionService.cancelTransaction(walletTransfer.getDebitRef());
        }
        if(TransferStatus.PENDING.equals(walletTransfer.getCreditStatus())){
            transactionService.cancelTransaction(walletTransfer.getCreditRef());
        }
    }

    private WalletTransfer doReversal(WalletTransfer transfer)throws ApiException{
        Wallet debitWallet = walletService.validateBalances(transfer.getAmount()*-1,transfer.getCreditWallet(),true,true);
        Wallet creditWallet = walletService.validateBalances(transfer.getAmount(),transfer.getDebitWallet(),true,true);
        WalletTransaction debitTransaction = transactionService.startInsert(WalletInsert
                .builder()
                .amount(transfer.getAmount())
                .data(transfer.getData())
                .wallet(debitWallet)
                .isDebit(true)
                .narration("RVSL "+transfer.getNarration())
                .ref(transfer.getTransferRefId())
                .build());
        WalletTransaction creditTransaction = transactionService.startInsert(WalletInsert
                .builder()
                .wallet(creditWallet)
                .isDebit(false)
                .ref(transfer.getTransferRefId())
                .narration("RVSL "+transfer.getNarration())
                .data(transfer.getData())
                .amount(transfer.getAmount())
                .build());
        transfer.setReversalCreditRef(creditTransaction.getReference());
        transfer.setReversalDebitRef(debitTransaction.getReference());
        transactionService.completeInsert(transfer.getReversalDebitRef());
        transactionService.completeInsert(transfer.getReversalCreditRef());
        walletService.updateBalances(transfer.getAmount()*-1,transfer.getCreditWallet(),true,true);
        walletService.updateBalances(transfer.getAmount(),transfer.getDebitWallet(),true,true);
        transfer.setCreditStatus(TransferStatus.REVERSED);
        transfer.setDebitStatus(TransferStatus.REVERSED);
        transfer.setReversedAt(DateTimeUtils.now());
        return repo.save(transfer);
    }

    @Override
    public WalletTransfer getTransfer(String transferRef) throws TransferRequestNotFoundException {
        return repo.findByTransferRefId(transferRef).orElseThrow(TransferRequestNotFoundException::new);
    }

    @Override
    public WalletTransfer getTransfer(String requestRef, String clientId) throws TransferRequestNotFoundException {
        return repo.findFirstByRequestRefIdAndClientId(requestRef, clientId).orElseThrow(TransferRequestNotFoundException::new);
    }

    private WalletTransfer createWalletTransfer(WalletTransferServiceRequest request) throws DuplicateTransferReferenceException {
        if(repo.findFirstByRequestRefIdAndClientId(request.getReference(),request.getClientId()).isPresent()){
            throw new DuplicateTransferReferenceException();
        }
        WalletTransfer transfer = new WalletTransfer();
        transfer.setTransferRefId(getRef());
        transfer.setData(request.getAdditionalData());
        transfer.setAmount(request.getAmount());
        transfer.setDebitWallet(request.getDebitWalletKey());
        transfer.setCreditWallet(request.getCreditWalletKey());
        transfer.setClientId(request.getClientId());
        transfer.setRequestRefId(request.getReference());
        transfer.setNarration(request.getNarration());
        return repo.save(transfer);
    }

    private String getRef(){
        return IdGenerator.idFromDateTime()+IdGenerator.randomNumbers(4);
    }
}
