package ru.otus.spring.hw5.service.exception;

public class NoAuthorFoundException extends RuntimeException {

    public NoAuthorFoundException(String message,
                                  Throwable cause) {
        super(message, cause);
    }

}
