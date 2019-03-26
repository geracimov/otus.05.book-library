package ru.otus.spring.hw5.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw5.dao.GenreDao;
import ru.otus.spring.hw5.dao.annotations.Tablename;
import ru.otus.spring.hw5.domain.Genre;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static ru.otus.spring.hw5.helpers.UuidHelper.getUuid;

@Repository
@Tablename(name = "genre")
public class GenreDaoJdbc implements GenreDao {

    final private NamedParameterJdbcOperations njdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations njdbc) {
        this.njdbc = njdbc;
    }

    @Override
    public Optional<Genre> getById(UUID id) {
        if (id == null) { return Optional.empty(); }
        String query = "SELECT id, name FROM " + getTableName() + " WHERE id=:id";

        return Optional.ofNullable(njdbc.queryForObject(query, Map.of("id", id), getRowMapper()));
    }

    @Override
    public List<Genre> getAll() {
        String query = "select * from " + getTableName();
        return njdbc.query(query, getRowMapper());
    }

    @Override
    public int insert(Genre genre) {
        String query = "insert into " + getTableName() + " (id, name) values (:id, :name)";
        return njdbc.update(query, Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public int update(Genre genre) {
        String query = "update " + getTableName() + " set name=:name where id=:id";
        return njdbc.update(query, Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public int delete(Genre genre) {
        String query = "delete from " + getTableName() + " where id=:id";
        return njdbc.update(query, Map.of("id", genre.getId()));
    }

    @Override
    public RowMapper<Genre> getRowMapper() {
        return (ResultSet rs, int i) -> new Genre(getUuid(rs.getString("id")), rs.getString("name"));
    }

    @Override
    public List<Genre> findByName(String name) {
        String query = "SELECT id, name FROM " + getTableName() + " WHERE name like :cname";

        return njdbc.query(query, Map.of("cname", '%' + name + '%'), getRowMapper());
    }

    @Override
    public Genre getByName(String name) {
        String query = "SELECT id, name FROM " + getTableName() + " WHERE name=:cname";

        return njdbc.queryForObject(query, Map.of("cname", name), getRowMapper());
    }
}
