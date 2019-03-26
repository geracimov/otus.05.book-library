package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.Author;

import java.util.List;

public interface AuthorDao extends Dao<Author> {
    List<Author> findByName(String name);

    Author getByName(String name);
}
