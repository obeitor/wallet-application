package com.nimisitech.wallet.integration.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceResponse {
    private Long availableBalanceMinor;
    private Long bookBalanceMinor;
    private String currency;
    private String walletId;

    @JsonProperty("available")
    public BigDecimal getAvailableBalance(){
        return BigDecimal.valueOf(this.availableBalanceMinor).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
    }

    @JsonProperty("book")
    public BigDecimal getBookBalance(){
        return BigDecimal.valueOf(this.bookBalanceMinor).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
    }
}
