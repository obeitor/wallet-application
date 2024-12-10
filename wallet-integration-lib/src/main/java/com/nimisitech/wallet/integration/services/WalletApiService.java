package com.nimisitech.wallet.integration.services;

import com.nimisitech.wallet.integration.apimodels.TransferRequest;
import com.nimisitech.wallet.integration.apimodels.responses.*;
import com.nimisitech.wallet.integration.exceptions.WalletServiceException;

import java.util.List;

public interface WalletApiService {
    BalanceApiResponse balanceEnquiry(String walletId) throws WalletServiceException;
    NameEnquireResponse getWalletName(String walletId)throws WalletServiceException;
    WalletResponse createWallet(String walletName, String walletCode)throws WalletServiceException;
    TransferApiResponse transferFunds(TransferRequest request)throws WalletServiceException;
    TransferApiResponse reverseFunds(String transferRef)throws WalletServiceException;
    TransferApiResponse getTransfer(String transferRef)throws WalletServiceException;
    List<TransactionApiResponse> getTransactions(String walletId)throws WalletServiceException;
}
