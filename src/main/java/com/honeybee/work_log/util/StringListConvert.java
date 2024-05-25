package com.honeybee.work_log.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class StringListConvert implements AttributeConverter<List<String>, String> {

    final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> strings) {

        try {
            return mapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<String> convertToEntityAttribute(String s) {
        try {
            return mapper.readValue(s, List.class);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
