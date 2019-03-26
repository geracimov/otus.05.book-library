package ru.otus.spring.hw5.dao.impl;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw5.dao.AuthorRefDao;
import ru.otus.spring.hw5.dao.annotations.Tablename;
import ru.otus.spring.hw5.domain.AuthorRef;

import java.sql.ResultSet;
import java.util.*;

import static ru.otus.spring.hw5.helpers.UuidHelper.getUuid;

@Repository
@Tablename(name = "book_author")
public class AuthorRefDaoJdbc implements AuthorRefDao {

    final private NamedParameterJdbcOperations njdbc;

    public AuthorRefDaoJdbc(NamedParameterJdbcOperations njdbc) {
        this.njdbc = njdbc;
    }

    @Override
    public Optional<AuthorRef> getById(UUID id) {
        if (id == null) { return Optional.empty(); }
        String query = "SELECT id, book_id, author_id  FROM " + getTableName() + " WHERE id=:id";

        return Optional.ofNullable(njdbc.queryForObject(query, Map.of("id", id), getRowMapper()));
    }

    @Override
    public List<AuthorRef> getAll() {
        String query = "select * from " + getTableName();
        return njdbc.query(query, getRowMapper());
    }


    @Override
    public int insert(AuthorRef authorRef) {
        String query =
                "insert into " + getTableName() + " (id, book_id, author_id)  values (:id, :book_id, :author_id)";
        return njdbc.update(query,
                            Map.of("id",
                                   authorRef.getId(),
                                   "book_id",
                                   authorRef.getBookId(),
                                   "author_id",
                                   authorRef.getAuthorId()));
    }

    @Override
    public int update(AuthorRef authorRef) {
        String query = "update " + getTableName() + " set book_id=:book_id, author_id=:author_id where id=:id";
        return njdbc.update(query,
                            Map.of("id",
                                   authorRef.getId(),
                                   "book_id",
                                   authorRef.getBookId(),
                                   "author_id",
                                   authorRef.getAuthorId()));
    }

    @Override
    public int delete(AuthorRef authorRef) {
        String query = "delete from " + getTableName() + " where id=:id";
        return njdbc.update(query, Map.of("id", authorRef.getId()));
    }

    @Override
    public RowMapper<AuthorRef> getRowMapper() {
        return (ResultSet rs, int i) -> new AuthorRef(getUuid(rs.getString("id")),
                                                      getUuid(rs.getString("book_id")),
                                                      getUuid(rs.getString("author_id")));
    }

    @Override
    public List<AuthorRef> getAllByBookId(UUID bookId) {
        String query = "select * from " + getTableName() + " where book_id=:book_id";
        return njdbc.query(query, Map.of("book_id", bookId), getRowMapper());
    }

    @Override
    public int insert(List<AuthorRef> authorRefs) {
        String sql = "INSERT INTO book_author (id, book_id, author_id) " + "VALUES (:id, :bookId, :authorId)";

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(authorRefs.toArray());
        int[] updateCounts = njdbc.batchUpdate(sql, batch);
        return Arrays.stream(updateCounts)
                     .sum();
    }
}
