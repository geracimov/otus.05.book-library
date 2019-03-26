package ru.otus.spring.hw5.dao.impl;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw5.dao.AuthorRefDao;
import ru.otus.spring.hw5.dao.BookDao;
import ru.otus.spring.hw5.dao.GenreRefDao;
import ru.otus.spring.hw5.dao.annotations.Tablename;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;
import ru.otus.spring.hw5.domain.GenreRef;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static ru.otus.spring.hw5.helpers.UuidHelper.getUuid;
import static ru.otus.spring.hw5.helpers.UuidHelper.newUuid;

@Repository
@Tablename(name = "book")
public class BookDaoJdbc implements BookDao {

    final private NamedParameterJdbcOperations njdbc;
    final private AuthorRefDao authorRefDao;
    final private GenreRefDao genreRefDao;


    public BookDaoJdbc(NamedParameterJdbcOperations njdbc,
                       AuthorRefDao authorRefDao,
                       GenreRefDao genreRefDao) {
        this.njdbc = njdbc;
        this.authorRefDao = authorRefDao;
        this.genreRefDao = genreRefDao;
    }

    @Override
    public Optional<Book> getById(UUID id) {
        if (id == null) { return Optional.empty(); }
        String query = "SELECT *  FROM " + getTableName() + " WHERE id=:id";
        Book book;
        try {
            book = njdbc.queryForObject(query, Map.of("id", id), getRowMapper());
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {return Optional.empty();}
    }

    @Override
    public List<Book> getAll() {
        String query = "select * from " + getTableName();
        return njdbc.query(query, getRowMapper());
    }


    @Override
    @Transactional
    public int insert(Book book) {
        String query = "insert into "
                       + getTableName()
                       + " (id, name, year, page_count, isbn ) values (:id, :name, :year, :page_count, :isbn )";
        int ins = njdbc.update(query,
                               Map.of("id",
                                      book.getId(),
                                      "name",
                                      book.getName(),
                                      "year",
                                      book.getYear(),
                                      "page_count",
                                      book.getPageCount(),
                                      "isbn",
                                      book.getIsbn()));
        genreRefDao.insert(book.getGenres());
        authorRefDao.insert(book.getAuthors());
        return ins;
    }

    @Override
    public int update(Book book) {
        String query = "update "
                       + getTableName()
                       + " set name=:name, year=:year, page_count=:page_count, isbn=:isbn  where id=:id";
        return njdbc.update(query,
                            Map.of("id",
                                   book.getId(),
                                   "name",
                                   book.getName(),
                                   "year",
                                   book.getYear(),
                                   "page_count",
                                   book.getPageCount(),
                                   "isbn",
                                   book.getIsbn()));
    }

    @Override
    public int delete(Book book) {
        String query = "delete from " + getTableName() + " where id=:id";
        return njdbc.update(query, Map.of("id", book.getId()));
    }

    @Override
    public RowMapper<Book> getRowMapper() {
        return (ResultSet rs, int i) -> new Book(getUuid(rs.getString("id")),
                                                 rs.getString("name"),
                                                 rs.getShort("year"),
                                                 rs.getShort("page_count"),
                                                 rs.getString("isbn"),
                                                 genreRefDao.getAllByBookId(getUuid(rs.getString("id"))),
                                                 authorRefDao.getAllByBookId(getUuid(rs.getString("id"))));
    }

    @Override
    public List<Book> findByName(String name) {
        String query = "SELECT * FROM " + getTableName() + " WHERE name like :name";

        return njdbc.query(query, Map.of("name", '%' + name + '%'), getRowMapper());
    }

    @Override
    public Book getByName(String name) {
        String query = "SELECT * FROM " + getTableName() + " WHERE name = :name";

        return njdbc.queryForObject(query, Map.of("name", name), getRowMapper());
    }

    @Override
    public void addGenreToBook(Book book,
                               Genre genre) {
        GenreRef ref = new GenreRef(newUuid(), book.getId(), genre.getId());
        genreRefDao.insert(ref);
    }

    @Override
    public void removeGenreFromBook(Book book,
                                    Genre genre) {
        GenreRef ref = new GenreRef(newUuid(), book.getId(), genre.getId());
        genreRefDao.insert(ref);
        book.getGenres()
            .add(ref);
    }
}
