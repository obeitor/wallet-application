package com.nimisitech.wallet.lib.exceptions;

public enum ApiResponseCode {
    SUCCESS("00"),UNKNOWN_ERROR("99"),INCORRECT_TASK("98"),
    INVALID_CLIENT("97"),
    BAD_REQUEST("01"),UNKNOWN_CURRENCY("02"),
    UNKNOWN_LIMIT_TYPE("03"),UNKNOWN_WALLET_TYPE("04"),WALLET_NOT_FOUND("05"),
    WALLET_INSUFFICIENT("06"),WALLET_ABOVE_MAX_BALANCE("07"),TRANSACTION_NOT_FOUND("08"),
    TRANSACTION_ALREADY_COMPLTED("09"),WALLET_CANNOT_CREDIT("10"),WALLET_CANNOT_DEBIT("11"),
    INVALID_AMOUNT("12"),DUPLICATE_TRANSFER_REF("13"),TRANSFER_NOT_FOUND("14"),
    TRANSFER_ALREADY_REVERSED("15"),TRANSFER_NOT_COMPLETED("16"),REVERSAL_NOT_PERMITTED("17");

    private String code;
    private ApiResponseCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
}
