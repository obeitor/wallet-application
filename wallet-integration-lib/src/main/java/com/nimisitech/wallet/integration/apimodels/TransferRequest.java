package com.nimisitech.wallet.integration.apimodels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class  TransferRequest {
    private Long amount;
    private String debitWalletKey;
    private String creditWalletKey;
    private String narration;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,30}$", message = "Reference must be at least 8 character, at most 30 characters and must be alpha-numeric only")
    private String reference;
    private Map<String,Object> additionalData;
}
