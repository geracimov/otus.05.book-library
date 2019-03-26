package ru.otus.spring.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Genre {

    private final UUID id;
    private String name;
}
