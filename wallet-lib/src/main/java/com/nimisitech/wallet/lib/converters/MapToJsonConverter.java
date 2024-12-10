package com.nimisitech.wallet.lib.converters;

import com.google.gson.Gson;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Map;

@Converter
public class MapToJsonConverter implements AttributeConverter<Map<String,Object>,String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> map) {
        return new Gson().toJson(map);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        return new Gson().fromJson(s,Map.class);
    }
}
