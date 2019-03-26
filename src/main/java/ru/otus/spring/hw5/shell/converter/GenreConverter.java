package ru.otus.spring.hw5.shell.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw5.domain.Genre;
import ru.otus.spring.hw5.service.GenreService;

@Component
class GenreConverter implements Converter<String, Genre> {

    private final GenreService genreService;

    GenreConverter(GenreService genreService) {this.genreService = genreService;}

    @Override
    public Genre convert(String name) {
        return genreService.getByName(name);
    }
}
