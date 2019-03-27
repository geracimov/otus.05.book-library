package ru.otus.spring.hw5.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw5.dao.BookDao;
import ru.otus.spring.hw5.domain.*;
import ru.otus.spring.hw5.service.BookService;
import ru.otus.spring.hw5.service.exception.NoAuthorFoundException;

import java.util.List;

import static ru.otus.spring.hw5.helpers.UuidHelper.newUuid;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {this.bookDao = bookDao;}

    @Override
    public List<Book> findByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    public void addAuthorToBook(Author author,
                                Book book) {

    }

    @Override
    public int addBook(String name,
                       short year,
                       short pageCount,
                       String isbn,
                       Author author,
                       Genre genre) {
        Book book = Book.builder()
                        .id(newUuid())
                        .isbn(isbn)
                        .name(name)
                        .pageCount(pageCount)
                        .year(year)
                        .build();
        List<GenreRef> genres = List.of(new GenreRef(newUuid(), book.getId(), genre.getId()));
        List<AuthorRef> authors = List.of(new AuthorRef(newUuid(), book.getId(), author.getId()));
        book.setGenres(genres);
        book.setAuthors(authors);
        return bookDao.insert(book);
    }

    @Override
    public Book getByName(String name) {
        try {
            return bookDao.getByName(name);
        } catch (EmptyResultDataAccessException e) {
            throw new NoAuthorFoundException("Книга с таким названием не найдена!", e);
        }
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
