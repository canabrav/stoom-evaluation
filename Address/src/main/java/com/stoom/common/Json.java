package com.stoom.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String stringify(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public static <TYPE> TYPE parse(String value, Class<TYPE> clazz) throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(value, clazz);
    }

    public static <TYPE> TYPE parse(String value, TypeReference<TYPE> type) throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(value, type);
    }

}
