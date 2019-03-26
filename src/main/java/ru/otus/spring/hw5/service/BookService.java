package ru.otus.spring.hw5.service;

import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;

import java.util.List;

public interface BookService {
    List<Book> findByName(String name);

    void addAuthorToBook(Author author,
                         Book book);

    int addBook(String name,
                short year,
                short pageCount,
                String isbn,
                Author author,
                Genre genre);

    Book getByName(String name);

    List<Book> getAll();
}
