package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.GenreRef;

import java.util.List;
import java.util.UUID;

public interface GenreRefDao extends Dao<GenreRef> {
    List<GenreRef> getAllByBookId(UUID bookId);

    int insert(List<GenreRef> genreRefs);
}
