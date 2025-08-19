package com.aluracursos.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadosData(
    List<LibrosInfo> results
) {
}
