package com.nimisitech.wallet.integration.apimodels.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NameEnquireResponse {
    private String walletName;
    private String walletId;
    private String currencyCode;
}
