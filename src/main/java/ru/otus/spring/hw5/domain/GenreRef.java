package ru.otus.spring.hw5.domain;


import lombok.Data;

import java.util.UUID;

@Data
public class GenreRef {

    private final UUID id;
    private final UUID bookId;
    private final UUID genreId;
}
