package com.nimisitech.wallet.integration.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceApiResponse {
    private String currency;
    private String walletId;
    private BigDecimal available;
    private BigDecimal book;
    private Long availableBalanceMinor;
    private Long bookBalanceMinor;
}
