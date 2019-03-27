package ru.otus.spring.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Author {

    private final UUID id;
    private String name;
    private LocalDate birth;
}
