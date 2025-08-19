package com.aluracursos.literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransformData implements ITransformData {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T readData(String json, Class<T> dataClass) {
        try {
            return mapper.readValue(json, dataClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
