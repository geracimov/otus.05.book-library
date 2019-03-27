package ru.otus.spring.hw5.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.domain.Genre;
import ru.otus.spring.hw5.service.AuthorService;
import ru.otus.spring.hw5.service.BookService;
import ru.otus.spring.hw5.service.GenreService;

import java.time.LocalDate;

@ShellComponent

public class Commands {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;

    public Commands(AuthorService authorService,
                    BookService bookService,
                    GenreService genreService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Book's list", group = "book")
    public void book(@ShellOption(defaultValue = "") String name) {
        if (name.isEmpty()) {
            bookService.getAll()
                       .
                               forEach(System.out::println);
        } else {
            System.out.println("Books with name like - " + name + ":");
            bookService.findByName(name)
                       .forEach(System.out::println);
        }
    }

    @ShellMethod(value = "Add new book to library", group = "book", key = "book add")
    public void bookAdd(String name,
                        short year,
                        short pageCount,
                        String isbn,
                        Author author,
                        Genre genre) {
        bookService.addBook(name, year, pageCount, isbn, author, genre);
        System.out.println("Book added");
    }

    @ShellMethod(value = "Author's list", group = "author")
    public void author(@ShellOption(defaultValue = "") String name) {
        if (name.isEmpty()) {
            authorService.getAll()
                         .forEach(System.out::println);
        } else {
            System.out.println("Authors with name like - " + name + ":");
            authorService.findByName(name)
                         .forEach(System.out::println);
        }
    }

    @ShellMethod(value = "Add author by name and birth (YYYY-MM-DD)", group = "author", key = "author add")
    public String authorAdd(String name,
                            LocalDate birth) {
        authorService.addAuthor(name, birth);
        return name + " added";
    }

    @ShellMethod(value = "Genre's list", group = "genre")
    public void genre(@ShellOption(defaultValue = "") String name) {
        if (name.isEmpty()) {
            genreService.getAll()
                        .forEach(System.out::println);
        } else {
            System.out.println("Genres with name like - " + name + ":");
            genreService.findByName(name)
                        .forEach(System.out::println);
        }
    }

    @ShellMethod(value = "Add genre by name", group = "genre", key = "genre add")
    public String genreAdd(String name) {
        genreService.addGenre(name);
        return name + " added";
    }

}


