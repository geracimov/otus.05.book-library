package ru.otus.spring.hw5.service;

import ru.otus.spring.hw5.domain.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {
    List<Author> findByName(String name);

    List<Author> getAll();

    Author getByName(String name);

    void addAuthor(String name,
                   LocalDate birth);
}
