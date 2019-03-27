package ru.otus.spring.hw5.dao;

import ru.otus.spring.hw5.domain.AuthorRef;

import java.util.List;
import java.util.UUID;

public interface AuthorRefDao extends Dao<AuthorRef> {
    List<AuthorRef> getAllByBookId(UUID bookId);

    int insert(List<AuthorRef> authorRefs);
}
