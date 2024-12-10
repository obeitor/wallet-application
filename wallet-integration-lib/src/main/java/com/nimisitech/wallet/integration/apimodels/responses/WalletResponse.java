package com.nimisitech.wallet.integration.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletResponse {
    private String name;
    private String id;
    private Boolean canDebit;
    private Boolean canCredit;
    private String status;
    private String currencyCode;
    private String limitCode;
    private String typeCode;
    private String limitName;
    private String typeName;
}
