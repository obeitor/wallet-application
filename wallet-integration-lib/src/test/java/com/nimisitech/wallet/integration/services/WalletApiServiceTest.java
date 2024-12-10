package com.nimisitech.wallet.integration.services;

import com.nimisitech.wallet.integration.apimodels.TransferRequest;
import com.nimisitech.wallet.integration.apimodels.responses.*;
import com.nimisitech.wallet.integration.clients.ServiceClient;
import com.nimisitech.wallet.integration.exceptions.WalletServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WalletApiServiceTest {

    private WalletApiService service;

    @Before
    public void setUp() {
        HttpComponentsClientHttpRequestFactory simpleClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(60000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);
        service = new WalletApiServiceImpl(ServiceClient.builder()
                .baseUrl("http://localhost:9090")
                .clientId("xyz")
                .restTemplate(restTemplate)
                .build());
    }

    @Test
    public void balanceEnquiry() throws WalletServiceException {
        BalanceApiResponse response = service.balanceEnquiry("f72f8b2cd6fd4a489f83e6b8771df242");
        Assert.assertEquals(0.0,response.getAvailable().doubleValue(),0);
    }

    @Test
    public void getWalletName() throws WalletServiceException {
        NameEnquireResponse response = service.getWalletName("f72f8b2cd6fd4a489f83e6b8771df242");
        Assert.assertEquals("VIRTUAL CARD COLLECTIONS",response.getWalletName());
    }

    @Test
    public void testGetWalletName() throws WalletServiceException {
        WalletResponse response = service.createWallet("TEST Wallet","W885");
        Assert.assertEquals("W885",response.getTypeCode());
        Assert.assertEquals("TEST WALLET",response.getName());
        Assert.assertFalse(response.getId().isEmpty());
    }
    @Test
    public void testTransfer()throws WalletServiceException{
        TransferRequest request = new TransferRequest();
        request.setNarration("Testing transfer");
        request.setReference("REFERENCE001");
        request.setDebitWalletKey("51a670a008774db9bff0db3936bb26c2");
        request.setCreditWalletKey("d6f108931320443d969d21b8997d098a");
        request.setAmount(50000L);
        request.setAdditionalData(new HashMap<>());
        TransferApiResponse rsp = service.transferFunds(request);
        Assert.assertEquals("COMPLETE",rsp.getCreditStatus());
        Assert.assertEquals("COMPLETE",rsp.getDebitStatus());
    }

    @Test
    public void testReverse()throws WalletServiceException{
        TransferRequest request = new TransferRequest();
        request.setNarration("Testing transfer");
        request.setReference("REFERENCE003");
        request.setDebitWalletKey("51a670a008774db9bff0db3936bb26c2");
        request.setCreditWalletKey("d6f108931320443d969d21b8997d098a");
        request.setAmount(50000L);
        request.setAdditionalData(new HashMap<>());
        TransferApiResponse rsp = service.transferFunds(request);
        TransferApiResponse revRsp = service.reverseFunds(rsp.getTransferRefId());
        Assert.assertEquals("REVERSED",revRsp.getDebitStatus());
        Assert.assertEquals("REVERSED",revRsp.getCreditStatus());
    }

    @Test
    public void getTransaction()throws WalletServiceException{
        List<TransactionApiResponse> responses = service.getTransactions("51a670a008774db9bff0db3936bb26c2");
        System.out.println(responses.size());
        Assert.assertFalse(responses.isEmpty());
    }

}