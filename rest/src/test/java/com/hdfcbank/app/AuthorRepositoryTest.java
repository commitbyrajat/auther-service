package com.hdfcbank.app;


import com.hdfcbank.app.v1.entities.Author;
import com.hdfcbank.app.v1.entities.Book;
import com.hdfcbank.app.v1.repository.AuthorRepository;
import com.hdfcbank.app.v1.repository.BookRepository;
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

    @Test
    void testSaveAndFindAuthor() {
        Author author = new Author();
        author.setName("New Author");

        StepVerifier.create(authorRepository.save(author)
                        .flatMap(saved -> authorRepository.findById(saved.getId())))
                .assertNext(found -> {
                    assert found.getId() != null;
                    assert found.getName().equals("New Author");
                })
                .verifyComplete();
    }

    @Test
    void testSaveAndFindBook() {
        Author author = new Author();
        author.setName("Book Author");

        StepVerifier.create(authorRepository.save(author)
                        .flatMap(savedAuthor -> {
                            Book book = new Book();
                            book.setTitle("Reactive Spring");
                            book.setAuthorId(savedAuthor.getId());
                            return bookRepository.save(book)
                                    .flatMap(savedBook -> bookRepository.findById(savedBook.getId()));
                        }))
                .assertNext(foundBook -> {
                    assert foundBook.getId() != null;
                    assert foundBook.getTitle().equals("Reactive Spring");
                })
                .verifyComplete();
    }

    @Test
    void testFindAuthorById() {
        StepVerifier.create(authorRepository.findById(1L))
                .assertNext(author -> {
                    assert author.getId() == 1L;
                    assert author.getName() != null;
                })
                .verifyComplete();
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setName("To Be Deleted");

        StepVerifier.create(authorRepository.save(author)
                        .flatMap(saved -> authorRepository.deleteById(saved.getId())
                                .then(authorRepository.findById(saved.getId()))))
                .verifyComplete(); // should emit no values
    }

    @Test
    void testFindBooksByInvalidAuthor() {
        StepVerifier.create(bookRepository.findByAuthorId(9999L))
                .verifyComplete(); // no books should be returned
    }
}
