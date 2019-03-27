package ru.otus.spring.hw5.shell.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String name) {
        return LocalDate.parse(name);
    }
}
