package com.nimisitech.wallet.lib.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Converter
public class DateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

    public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        } else {
            Instant instant = localDateTime.atZone(ZoneId.of("GMT+1")).toInstant();
            return Date.from(instant);
        }
    }

    public LocalDateTime convertToEntityAttribute(Date date) {
        if (date == null) {
            return null;
        } else {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            return LocalDateTime.ofInstant(instant, ZoneId.of("GMT+1"));
        }
    }
}
