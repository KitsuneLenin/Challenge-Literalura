package com.aluracursos.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibrosInfo(
        String title,
        List<AutoresInfo> authors,
        @JsonAlias("languages") List<String> language,
        @JsonAlias("download_count") Integer downloadCount
) {
    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", language=" + language +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
