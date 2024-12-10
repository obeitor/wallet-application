package com.nimisitech.wallet.lib.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class WalletTransferServiceRequest {
    private Long amount;
    private String debitWalletKey;
    private String creditWalletKey;
    private String narration;
    private String reference;
    private String clientId;
    private Map<String,Object> additionalData;

    public Long getDebitAmount(){
        return -1*amount;
    }
}
