package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;

import java.util.List;

public interface BookDao extends Dao<Book> {
    List<Book> findByName(String name);

    Book getByName(String name);

    void addGenreToBook(Book book,
                        Genre genre);

    void removeGenreFromBook(Book book,
                             Genre genre);
}
