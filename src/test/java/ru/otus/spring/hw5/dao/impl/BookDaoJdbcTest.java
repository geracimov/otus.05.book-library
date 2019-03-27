package ru.otus.spring.hw5.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw5.dao.BookDao;
import ru.otus.spring.hw5.dao.GenreRefDao;
import ru.otus.spring.hw5.domain.AuthorRef;
import ru.otus.spring.hw5.domain.Book;
import ru.otus.spring.hw5.domain.Genre;
import ru.otus.spring.hw5.domain.GenreRef;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan
@DisplayName("Check that BookDaoJdbc")
class BookDaoJdbcTest {
    private static Book book1;
    private static GenreRef genreRef11;
    private static AuthorRef authorRef11;
    private static Genre fantastic;
    @Autowired
    BookDao bookDao;
    @Autowired
    GenreRefDao genreRefDao;

    @BeforeAll
    static void init() {
        genreRef11 = new GenreRef(UUID.fromString("996ad860-2a9a-504f-8861-aeafd0b2ae29"),
                                  UUID.fromString("802ae788-4c31-4d92-9055-bfcc6e9cfc5c"),
                                  UUID.fromString("ee1fd24c-786b-59c4-a712-4428ee65b61d"));

        authorRef11 = new AuthorRef(UUID.fromString("996ad860-2a9a-504f-8861-aeafd0b2ae29"),
                                    UUID.fromString("802ae788-4c31-4d92-9055-bfcc6e9cfc5c"),
                                    UUID.fromString("2dabc86c-fc3a-58d3-b199-7386415ae67d"));


        book1 = new Book(UUID.fromString("802ae788-4c31-4d92-9055-bfcc6e9cfc5c"),
                         "book1",
                         (short) 2007,
                         (short) 121,
                         "ISBN 5-17-008508-7",
                         List.of(genreRef11),
                         List.of(authorRef11));
        fantastic = new Genre(UUID.fromString("52b18bcc-3a12-518b-8ee2-22132652a82c"), "Фантастика");
    }

    @Test
    @DisplayName("correctly get Book by ID")
    void getById() {
        Optional<Book> bookOpt = bookDao.getById(UUID.fromString("802ae788-4c31-4d92-9055-bfcc6e9cfc5c"));
        assertThat(bookOpt).isPresent()
                           .get()
                           .isNotNull();
        Book book = bookOpt.get();
        assertThat(book).hasNoNullFieldsOrProperties()
                        .hasFieldOrProperty("name")
                        .hasFieldOrProperty("year")
                        .hasFieldOrProperty("isbn")
                        .hasFieldOrProperty("pageCount");
        assertThat(book.getName()).isEqualTo("book1");
        assertThat(book.getIsbn()).isEqualTo("ISBN 5-17-008508-7");
    }

    @Test
    @DisplayName("return Empty oon non existing ID")
    void getByNotExistingId() {
        Optional<Book> bookOpt = bookDao.getById(UUID.randomUUID());
        assertThat(bookOpt).isEmpty();
    }

    @Test
    @DisplayName("return all books")
    void getAll() {
        assertThat(bookDao.getAll()).isNotNull()
                                    .isNotEmpty()
                                    .doesNotContainNull()
                                    .hasSize(4)
                                    .contains(bookDao.getById(UUID.fromString("802ae788-4c31-4d92-9055-bfcc6e9cfc5c"))
                                                     .get());

    }

    @Test
    @DisplayName("get  book by name")
    void getByName() {
        assertThat(bookDao.getByName("book2")).isNotNull()
                                              .isEqualTo(bookDao.getById(UUID.fromString("d8e483b0-eb6e"
                                                                                         + "-4c77-a100-d84e49bb35fc"))
                                                                .get());
    }

    @Test
    @DisplayName("find all books by name")
    void findByName() {
        assertThat(bookDao.findByName("book")).hasSize(4)
                                              .doesNotContainNull()
                                              .doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("add Genre to Book")
    void addGenreToBook() {
        UUID bookId = UUID.fromString("802ae788-4c31-4d92-9055-bfcc6e9cfc5c");
        assertThat(genreRefDao.getAllByBookId(bookId)).hasSize(1)
                                                      .contains(genreRef11);
        bookDao.addGenreToBook(book1, fantastic);
        assertThat(genreRefDao.getAllByBookId(bookId)).hasSize(2)
                                                      .contains(genreRef11);
    }
}