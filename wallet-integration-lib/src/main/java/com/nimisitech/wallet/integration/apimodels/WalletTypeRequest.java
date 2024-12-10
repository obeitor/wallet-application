package com.nimisitech.wallet.integration.apimodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTypeRequest {
    private String currencyCode;
    private String limitCode;
    private String name;
}
