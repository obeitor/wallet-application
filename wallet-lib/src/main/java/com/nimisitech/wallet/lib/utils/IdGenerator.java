package com.nimisitech.wallet.lib.utils;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class IdGenerator {
    public static String getUuidBasedId(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static String randomNumbers(int size){
        String alphanumeric = "1234567890";
        int alphaNumericLen = alphanumeric.length();
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<size; i++){
            builder.append(alphanumeric.charAt(new SecureRandom().nextInt(alphaNumericLen)));
        }
        return builder.toString();
    }

    public static String idFromDateTime(){
        return DateTimeUtils.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
    }
}
