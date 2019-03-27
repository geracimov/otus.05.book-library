package ru.otus.spring.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
//@RequiredArgsConstructor
public class Book {

    private final UUID id;
    @NonNull
    private String name;
    private short year;
    private short pageCount;
    @NonNull
    private String isbn;
    private List<GenreRef> genres;
    private List<AuthorRef> authors;
}
