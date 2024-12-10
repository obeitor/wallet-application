package com.nimisitech.wallet.service.apimodels;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nimisitech.wallet.integration.apimodels.TransferRequest;
import com.nimisitech.wallet.lib.dtos.WalletTransferServiceRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransferRequest extends TransferRequest {
    @JsonIgnore
    public WalletTransferServiceRequest getServiceRequest(String clientId){
        return WalletTransferServiceRequest
                .builder()
                .additionalData(this.getAdditionalData())
                .amount(this.getAmount())
                .clientId(clientId)
                .debitWalletKey(this.getDebitWalletKey())
                .creditWalletKey(this.getCreditWalletKey())
                .narration(this.getNarration())
                .reference(this.getReference())
                .build();
    }
}
