package ru.otus.spring.hw5.shell.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw5.domain.Author;
import ru.otus.spring.hw5.service.AuthorService;

@Component
class AuthorConverter implements Converter<String, Author> {

    private final AuthorService authorService;

    AuthorConverter(AuthorService authorService) {this.authorService = authorService;}

    @Override
    public Author convert(String name) {
        return authorService.getByName(name);
    }
}
