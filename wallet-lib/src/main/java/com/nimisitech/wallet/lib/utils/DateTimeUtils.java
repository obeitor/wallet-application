package com.nimisitech.wallet.lib.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String timeFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormat = "yyyy-MM-dd";

    public static LocalDateTime now(){
        return LocalDateTime.now(ZoneId.of("GMT+1"));
    }

    public static LocalDateTime parseDateTime(String dateTime){
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(timeFormat));
    }

    public static LocalDateTime parseDate(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String toString(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern(timeFormat));
    }

    public static String toString(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }
}
