package com.nimisitech.wallet.service.exceptions;

import com.nimisitech.wallet.lib.exceptions.ApiException;
import com.nimisitech.wallet.lib.exceptions.ApiResponseCode;

public class ClientException extends ApiException {
    public ClientException(String message, ApiResponseCode responseCode) {
        super(message, responseCode);
    }
}
