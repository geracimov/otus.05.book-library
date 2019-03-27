package ru.otus.spring.hw5.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw5.dao.AuthorDao;
import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.service.AuthorService;
import ru.otus.spring.hw5.service.exception.NoAuthorFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.otus.spring.hw5.helpers.UuidHelper.newUuid;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {this.authorDao = authorDao;}

    @Override
    public List<Author> findByName(String name) {
        return authorDao.findByName(name);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Author getByName(String name) {
        try {
            return authorDao.getByName(name);
        } catch (EmptyResultDataAccessException e) {
            throw new NoAuthorFoundException("Автор с таким именем не найден!", e);
        }
    }

    @Override
    public void addAuthor(String name,
                          LocalDate birth) {
        Author author = new Author(newUuid(), name, birth);
        authorDao.insert(author);
    }
}
