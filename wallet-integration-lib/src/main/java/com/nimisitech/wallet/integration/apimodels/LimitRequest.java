package com.nimisitech.wallet.integration.apimodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LimitRequest {
    private Long minimumBalance ;
    private Long maximumBalance ;
    private Long minimumSingleDebit ;
    private Long maximumSingleDebit ;
    private Long minimumSingleCredit;
    private Long maximumSingleCredit ;
    private Long minimumDailyDebit ;
    private Long maximumDailyDebit ;
    private Long minimumDailyCredit ;
    private Long maximumDailyCredit ;
    private String name;

}
