package com.nimisitech.wallet.integration.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nimisitech.wallet.integration.apimodels.TransferRequest;
import com.nimisitech.wallet.integration.apimodels.WalletCreationRequest;
import com.nimisitech.wallet.integration.apimodels.responses.*;
import com.nimisitech.wallet.integration.clients.ServiceClient;
import com.nimisitech.wallet.integration.exceptions.WalletServiceException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "wallet.api.service.integrator", havingValue = "true")
public class WalletApiServiceImpl implements WalletApiService{
    private final ServiceClient client;

    public BalanceApiResponse balanceEnquiry(String walletId) throws WalletServiceException {
        Object object = client.makeRequest(HttpMethod.GET,"/api/v1/wallet/balance/"+walletId,null);
        return new ObjectMapper().convertValue(object,BalanceApiResponse.class);
    }

    public NameEnquireResponse getWalletName(String walletId)throws WalletServiceException{
        Object object = client.makeRequest(HttpMethod.GET,"/api/v1/wallet/enquire/"+walletId,null);
        return new ObjectMapper().convertValue(object,NameEnquireResponse.class);
    }

    public WalletResponse createWallet(String walletName, String walletCode)throws WalletServiceException{
        WalletCreationRequest request = new WalletCreationRequest();
        request.setName(walletName);
        request.setWalletCode(walletCode);
        Object object = client.makeRequest(HttpMethod.POST,"/api/v1/wallet/create",request);
        return new ObjectMapper().convertValue(object,WalletResponse.class);
    }

    @Override
    public TransferApiResponse transferFunds(TransferRequest request) throws WalletServiceException {
        Object object = client.makeRequest(HttpMethod.POST,"/api/v1/transfer",request);
        return new ObjectMapper().convertValue(object,TransferApiResponse.class);
    }

    @Override
    public TransferApiResponse reverseFunds(String transferRef) throws WalletServiceException {
        Object object = client.makeRequest(HttpMethod.POST, "/api/v1/transfer/reverse/"+transferRef,null);
        return new ObjectMapper().convertValue(object,TransferApiResponse.class);
    }

    @Override
    public TransferApiResponse getTransfer(String transferRef) throws WalletServiceException {
        Object object = client.makeRequest(HttpMethod.GET,"/api/v1/transfer/"+transferRef,null);
        return new ObjectMapper().convertValue(object,TransferApiResponse.class);
    }

    @Override
    public List<TransactionApiResponse> getTransactions(String walletId) throws WalletServiceException {
        Object object = client.makeRequest(HttpMethod.GET, "/api/v1/transactions/page/"+walletId,null);
        log.info(new Gson().toJson(object));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object,mapper.getTypeFactory().constructCollectionType(List.class,TransactionApiResponse.class));
    }


}
