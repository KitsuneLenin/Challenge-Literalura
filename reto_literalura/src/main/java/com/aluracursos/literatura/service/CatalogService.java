package com.aluracursos.literatura.service;

import com.aluracursos.literatura.entity.Author;
import com.aluracursos.literatura.entity.Book;
import com.aluracursos.literatura.model.AutoresInfo;
import com.aluracursos.literatura.model.LibrosInfo;
import com.aluracursos.literatura.repository.AutoresRepository;
import com.aluracursos.literatura.repository.LibrosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final AutoresRepository autoresRepository;
    private final LibrosRepository librosRepository;
    private Scanner scanner = new Scanner(System.in);

    public CatalogService(AutoresRepository autoresRepository, LibrosRepository librosRepository) {
        this.autoresRepository = autoresRepository;
        this.librosRepository = librosRepository;
    }

    @Transactional
    public void savebook(LibrosInfo librosInfo) {
        AutoresInfo autoresInfo = librosInfo.authors().isEmpty()
                ? new AutoresInfo("Autor desconocido", null, null) : librosInfo.authors().get(0);

        Author author = autoresRepository.findByNameIgnoreCase(autoresInfo.name())
                .orElseGet(() -> {
                    Author newAuthor = new Author(autoresInfo.name(), autoresInfo.birthYear(), autoresInfo.deathYear());
                    return autoresRepository.save(newAuthor);
                });

        var bookName = librosRepository.findByTitleIgnoreCase(librosInfo.title());

        if (bookName.isPresent()) {
            System.out.println("No se puede registrar el mismo libro más de una vez");
            System.out.println();
            return;
        }

        Book newBook = new Book(librosInfo, author);
        librosRepository.save(newBook);
    }

    public void showSavedBooks() {
        List<Book> books = librosRepository.findAll();

        if (books.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos");
            return;
        }

        books.forEach(book -> {
            System.out.println("""
                            ----- LIBRO -----
                            Título: %s
                            Autor: %s
                            Idioma: %s
                            Número de descargas: %d
                            -----------------
                            """.formatted(book.getTitle(), book.getAuthor().getName(), book.getLanguage(), book.getDownloadCount()));
        });
    }

    public void showSavedAuthors() {
        List<Author> authors = autoresRepository.findAll();

        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos");
        }

        authors.forEach((author) -> {
            System.out.println("""
                    Autor: %s
                    Fecha de Nacimiento: %d
                    Fecha de Fallecimiento: %d
                    Libros: %s
                    """.formatted(author.getName(), author.getBirthYear(), author.getDeathYear(), author.getBooks().stream()
                    .map(Book::getTitle).collect(Collectors.toList())));
        });
    }

    public void showAuthorsAliveAtYear() {
        System.out.println("Ingrese el año para el que desea buscar que autor(es) se encontraban vivos");
        Integer year = scanner.nextInt();
        scanner.nextLine();

        System.out.println();
        List<Author> aliveAuthors = autoresRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
        if (aliveAuthors.isEmpty()) {
            System.out.println("No hay ningún autor registrado que se encontrara vivo durante el año %d".formatted(year));
            System.out.println();
        }

        aliveAuthors.forEach(author -> {
            System.out.println("""
                    Autor: %s
                    Fecha de Nacimiento: %d
                    Fecha de Fallecimiento: %d
                    Libros: %s
                    """.formatted(author.getName(), author.getBirthYear(), author.getDeathYear(), author.getBooks().stream()
                    .map(Book::getTitle).collect(Collectors.toList())));
        });
    }

    public void findBooksByLanguage() {
        System.out.println("""
                Ingrese el idioma para buscar los libros
                es- español
                en- inglés
                fr- francés
                pt- portugués""");

        String language = scanner.nextLine();

        List<Book> booksByLanguage = librosRepository.findByLanguage(language);
        System.out.println();

        if (!booksByLanguage.isEmpty()) {
            booksByLanguage.forEach(book -> {
                System.out.println("""
                            ----- LIBRO -----
                            Título: %s
                            Autor: %s
                            Idioma: %s
                            Número de descargas: %d
                            -----------------
                            """.formatted(book.getTitle(), book.getAuthor().getName(), book.getLanguage(), book.getDownloadCount()));
            });
        } else {
            System.out.println("No se encuentran libros en el idioma: %s".formatted(language));
            System.out.println();
        }

    }
}
