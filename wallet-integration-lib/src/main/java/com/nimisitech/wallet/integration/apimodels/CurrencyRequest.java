package com.nimisitech.wallet.integration.apimodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRequest {
    @Size(max = 3)
    @Pattern(regexp = "^[a-zA-Z]{3}$", message = "Currency code must be 3 alphabet characters e.g. NGN")
    private String currencyCode;
    @Pattern(regexp = "^[a-zA-Z0-9 ]{3,100}$", message = "Currency name must be alpha numeric only")
    private String name;
}
