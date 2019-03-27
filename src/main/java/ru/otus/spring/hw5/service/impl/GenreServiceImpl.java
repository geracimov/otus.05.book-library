package ru.otus.spring.hw5.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw5.dao.GenreDao;
import ru.otus.spring.hw5.domain.Genre;
import ru.otus.spring.hw5.service.GenreService;
import ru.otus.spring.hw5.service.exception.NoAuthorFoundException;

import java.util.List;

import static ru.otus.spring.hw5.helpers.UuidHelper.newUuid;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {this.genreDao = genreDao;}

    @Override
    public List<Genre> findByName(String name) {
        return genreDao.findByName(name);
    }

    @Override
    public void addGenre(String name) {
        Genre genre = new Genre(newUuid(), name);
        genreDao.insert(genre);
    }

    @Override
    public Genre getByName(String name) {
        try {
            return genreDao.getByName(name);
        } catch (EmptyResultDataAccessException e) {
            throw new NoAuthorFoundException("Жанр с таким названием не найден!", e);
        }
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
