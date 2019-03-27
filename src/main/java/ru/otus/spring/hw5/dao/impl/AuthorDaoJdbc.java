package ru.otus.spring.hw5.dao.impl;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw5.dao.AuthorDao;
import ru.otus.spring.hw5.dao.annotations.Tablename;
import ru.otus.spring.hw5.domain.Author;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static ru.otus.spring.hw5.helpers.UuidHelper.getUuid;

@Repository
@Tablename(name = "author")
public class AuthorDaoJdbc implements AuthorDao {

    final private NamedParameterJdbcOperations njdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations njdbc) {
        this.njdbc = njdbc;
    }

    @Override
    public Optional<Author> getById(UUID id) {
        if (id == null) { return Optional.empty(); }
        String query = "SELECT * FROM " + getTableName() + " WHERE id=:id";

        return Optional.ofNullable(njdbc.queryForObject(query, Map.of("id", id), getRowMapper()));
    }

    @Override
    public List<Author> getAll() {
        String query = "select * from " + getTableName();
        return njdbc.query(query, getRowMapper());
    }


    @Override
    public int insert(Author author) {
        String query = "insert into " + getTableName() + " (id, name, birth) " + " values (:id, :name, :birth)";
        return njdbc.update(query, Map.of("id", author.getId(), "name", author.getName(), "birth", author.getBirth()));
    }

    @Override
    public int update(Author author) {
        String query = "update " + getTableName() + " set name=:name, birth=:birthwhere id=:id";
        return njdbc.update(query, Map.of("id", author.getId(), "name", author.getName(), "birth", author.getBirth()));
    }

    @Override
    public int delete(Author author) {
        String query = "delete from " + getTableName() + " where id=:id";
        return njdbc.update(query, Map.of("id", author.getId()));
    }

    @Override
    public RowMapper<Author> getRowMapper() {
        return (ResultSet rs, int i) -> new Author(getUuid(rs.getString("id")),
                                                   rs.getString("name"),
                                                   rs.getDate("birth")
                                                     .toLocalDate());
    }

    @Override
    public Author getByName(String authorName) {
        String query = "SELECT * FROM " + getTableName() + " WHERE name = :authorName";

        return njdbc.queryForObject(query, Map.of("authorName", authorName), getRowMapper());
    }

    @Override
    public List<Author> findByName(String authorName) {
        String query = "SELECT * FROM " + getTableName() + " WHERE name like :authorName";

        return njdbc.query(query, Map.of("authorName", '%' + authorName + '%'), getRowMapper());
    }
}
