package ru.otus.spring.hw5.dao.impl;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw5.dao.GenreRefDao;
import ru.otus.spring.hw5.dao.annotations.Tablename;
import ru.otus.spring.hw5.domain.GenreRef;

import java.sql.ResultSet;
import java.util.*;

import static ru.otus.spring.hw5.helpers.UuidHelper.getUuid;

@Repository
@Tablename(name = "book_genre")
public class GenreRefDaoJdbc implements GenreRefDao {

    final private NamedParameterJdbcOperations njdbc;

    public GenreRefDaoJdbc(NamedParameterJdbcOperations njdbc) {
        this.njdbc = njdbc;
    }

    @Override
    public Optional<GenreRef> getById(UUID id) {
        if (id == null) { return Optional.empty(); }
        String query = "SELECT id, book_id, genre_id  FROM " + getTableName() + " WHERE id=:id";

        return Optional.ofNullable(njdbc.queryForObject(query, Map.of("id", id), getRowMapper()));
    }

    @Override
    public List<GenreRef> getAll() {
        String query = "select * from " + getTableName();
        return njdbc.query(query, getRowMapper());
    }


    @Override
    public int insert(GenreRef genreRef) {
        String query = "insert into " + getTableName() + " (id, book_id, genre_id)  values (:id, :book_id, :genre_id)";
        return njdbc.update(query,
                            Map.of("id",
                                   genreRef.getId(),
                                   "book_id",
                                   genreRef.getBookId(),
                                   "genre_id",
                                   genreRef.getGenreId()));
    }

    @Override
    public int update(GenreRef genreRef) {
        String query = "update " + getTableName() + " set book_id=:book_id, genre_id=:genre_id where id=:id";
        return njdbc.update(query,
                            Map.of("id",
                                   genreRef.getId(),
                                   "book_id",
                                   genreRef.getBookId(),
                                   "genre_id",
                                   genreRef.getGenreId()));
    }

    @Override
    public int delete(GenreRef genreRef) {
        String query = "delete from " + getTableName() + " where id=:id";
        return njdbc.update(query, Map.of("id", genreRef.getId()));
    }

    @Override
    public RowMapper<GenreRef> getRowMapper() {
        return (ResultSet rs, int i) -> new GenreRef(getUuid(rs.getString("id")),
                                                     getUuid(rs.getString("book_id")),
                                                     getUuid(rs.getString("genre_id")));
    }

    @Override
    public List<GenreRef> getAllByBookId(UUID bookId) {
        String query = "select * from " + getTableName() + " where book_id=:book_id";
        return njdbc.query(query, Map.of("book_id", bookId), getRowMapper());
    }

    @Override
    public int insert(List<GenreRef> genreRefs) {
        String sql = "INSERT INTO book_genre (id, book_id, genre_id) " + "VALUES (:id, :bookId, :genreId)";

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(genreRefs.toArray());
        int[] updateCounts = njdbc.batchUpdate(sql, batch);
        return Arrays.stream(updateCounts)
                     .sum();
    }
}
