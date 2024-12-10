package com.nimisitech.wallet.lib.service;

import com.nimisitech.wallet.lib.dtos.WalletTransferServiceRequest;
import com.nimisitech.wallet.lib.exceptions.ApiException;
import com.nimisitech.wallet.lib.exceptions.TransferRequestNotFoundException;
import com.nimisitech.wallet.lib.models.WalletTransfer;

public interface WalletTransferService {
    WalletTransfer performFullTransfer(WalletTransferServiceRequest request) throws ApiException;
    WalletTransfer performPartialTransfer(WalletTransferServiceRequest request)throws ApiException;
    WalletTransfer completeTransfer(String transferRef)throws ApiException;
    WalletTransfer performReversal(String transferRef) throws ApiException;
    WalletTransfer performReversal(String requestRef, String clientId) throws ApiException;
    WalletTransfer getTransfer(String transferRef) throws TransferRequestNotFoundException;
    WalletTransfer getTransfer(String requestRef,String clientId) throws TransferRequestNotFoundException;
}
