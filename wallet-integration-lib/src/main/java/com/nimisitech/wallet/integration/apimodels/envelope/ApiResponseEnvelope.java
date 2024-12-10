package com.nimisitech.wallet.integration.apimodels.envelope;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseEnvelope {
    private boolean success;
    private String message;
    private Object response;
    private String responseCode;


}
