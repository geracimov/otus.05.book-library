package ru.otus.spring.hw5.service;

import ru.otus.spring.hw5.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findByName(String name);

    void addGenre(String name);

    Genre getByName(String name);

    List<Genre> getAll();
}
