package ru.otus.spring.hw5.shell.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.service.BookService;

@Component
class BookConverter implements Converter<String, Book> {

    private final BookService bookService;

    BookConverter(BookService bookService) {this.bookService = bookService;}

    @Override
    public Book convert(String name) {
        return bookService.getByName(name);
    }
}
