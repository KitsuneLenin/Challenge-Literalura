package com.aluracursos.literatura.dto;

public record LibrosDTO(
        String title,
        String author,
        String language,
        Integer downloadCount
) {
}
