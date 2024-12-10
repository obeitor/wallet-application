package com.nimisitech.wallet.integration.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferApiResponse {
    private String debitWallet;
    private String creditWallet;
    private String completedAt;
    private String reversedAt;
    private Long amount;
    private String narration;
    private String requestRefId;
    private String transferRefId;
    private Map<String,Object> data;
    private String clientId;
    private String debitRef;
    private String creditRef;
    private String reversalCreditRef;
    private String reversalDebitRef;
    private String debitStatus;
    private String creditStatus;
}
