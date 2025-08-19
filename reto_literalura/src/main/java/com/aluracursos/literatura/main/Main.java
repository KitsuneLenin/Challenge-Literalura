package com.aluracursos.literatura.main;

import com.aluracursos.literatura.model.ResultadosData;
import com.aluracursos.literatura.service.CatalogService;
import com.aluracursos.literatura.service.ReadAPI;
import com.aluracursos.literatura.service.TransformData;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Scanner;

@Component
public class Main {
    public static final String BASE_URL = "https://gutendex.com/books/";
    private Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private ReadAPI readApi = new ReadAPI();
    private TransformData conversor = new TransformData();
    private final CatalogService catalog;

    public Main(CatalogService catalog) {
        this.catalog = catalog;
    }

    public void showMenu() {
        int userOption = -1;

        while (userOption != 0) {
            System.out.println("""
                    ------------
                    Elija la opción a través de su número:
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    0- Salir
                    """);
            userOption = scanner.nextInt();
            scanner.nextLine();
            
            switch (userOption) {
                case 1:
                    getBookInfo();
                    break;
                case 2:
                    catalog.showSavedBooks();
                    break;
                case 3:
                    catalog.showSavedAuthors();
                    break;
                case 4:
                    catalog.showAuthorsAliveAtYear();
                    break;
                case 5:
                    catalog.findBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Gracias por consultar LiterAlura");
                    break;
                default:
                    System.out.println("Por favor elija una opción válida");
                    break;
            }
        }
        System.out.println("Programa finalizado");
    }

    private void getBookInfo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var bookName = scanner.nextLine();

        String json = readApi.readData(BASE_URL + "?search=" + bookName.replace(" ", "+"));
        ResultadosData resultadosData = conversor.readData(json, ResultadosData.class);

        resultadosData.results().stream()
                .filter(book -> book.title().toLowerCase().contains(bookName.toLowerCase()))
                .findFirst()
                .ifPresentOrElse(book -> {
                    String authorName = book.authors().isEmpty() ? "Autor desconocido" : book.authors().get(0).name();
                    String language = book.language().isEmpty() ? "Idioma desconocido" : book.language().get(0);
                    Integer downloads = book.downloadCount() == null ? 0 : book.downloadCount();

                    System.out.println();
                    System.out.println("""
                            ----- LIBRO -----
                            Título: %s
                            Autor: %s
                            Idioma: %s
                            Número de descargas: %d
                            -----------------
                            """.formatted(book.title(), authorName, language, downloads)
                    );

                    catalog.savebook(book);
                }, () -> System.out.println("Libro no encontrado"));
    }
}
