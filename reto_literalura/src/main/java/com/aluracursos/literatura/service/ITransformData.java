package com.aluracursos.literatura.service;

public interface ITransformData {
    <T> T readData(String json, Class<T> dataClass);
}
