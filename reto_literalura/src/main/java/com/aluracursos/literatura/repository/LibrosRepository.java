package com.aluracursos.literatura.repository;

import com.aluracursos.literatura.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCase(String title);

    List<Book> findByLanguage(String language);
}
