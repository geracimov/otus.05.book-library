package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.Genre;

import java.util.List;


public interface GenreDao extends Dao<Genre> {
    Genre getByName(String name);

    List<Genre> findByName(String name);
}
