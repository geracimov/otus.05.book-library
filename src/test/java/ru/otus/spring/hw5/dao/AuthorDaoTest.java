package ru.otus.spring.hw5.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw5.domain.Author;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan
public class AuthorDaoTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    public void getById() {
        Optional<Author> authorOpt = authorDao.getById(UUID.fromString("2dabc86c-fc3a-58d3-b199-7386415ae67d"));
        System.out.println(authorOpt.get());
        System.out.println("sss");
    }

}