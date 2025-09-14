package com.hdfcbank.app;


import com.hdfcbank.app.repositories.AuthorRepository;
import com.hdfcbank.app.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("qa")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindAuthorsAndBooks() {
        StepVerifier.create(authorRepository.findAll())
                .expectNextCount(2)
                .verifyComplete();

        StepVerifier.create(bookRepository.findByAuthorId(1L))
                .expectNextCount(2)
                .verifyComplete();
    }


}
