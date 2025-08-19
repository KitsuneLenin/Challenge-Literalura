package com.aluracursos.literatura.repository;

import com.aluracursos.literatura.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameIgnoreCase(String name);

    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(Integer birthYear, Integer deathYear);
}
