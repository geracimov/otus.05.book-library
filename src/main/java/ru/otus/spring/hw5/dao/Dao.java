package ru.otus.spring.hw5.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.hw5.dao.annotations.Tablename;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Dao<T> {

    Optional<T> getById(UUID id);

    List<T> getAll();

    int insert(T entity);

    int update(T entity);

    int delete(T entity);

    RowMapper<T> getRowMapper();

    default String getTableName() {
        return getClass().getAnnotation(Tablename.class)
                         .name();
    }
}
