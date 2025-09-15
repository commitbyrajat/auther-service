package com.hdfcbank.app;


import com.hdfcbank.app.v1.entities.Author;
import com.hdfcbank.app.v1.entities.Book;
import com.hdfcbank.app.v1.repository.AuthorRepository;
import com.hdfcbank.app.v1.repository.BookRepository;
import com.hdfcbank.app.v1.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
@ActiveProfiles("qa")
class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindAuthorWithBooks() {
        Author author = new Author();
        author.setName("Service Author");

        Book b1 = new Book();
        b1.setTitle("Service Book 1");

        Book b2 = new Book();
        b2.setTitle("Service Book 2");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMap(savedAuthor -> {
                                    b1.setAuthorId(savedAuthor.getId());
                                    b2.setAuthorId(savedAuthor.getId());
                                    return bookRepository.saveAll(List.of(b1, b2)).then(Mono.just(savedAuthor.getId()));
                                })
                                .flatMap(id -> authorService.findAuthorWithBooks(id))
                )
                .expectNextMatches(a ->
                        a.getName().equals("Service Author")
                                && a.getBooks().size() == 2
                                && a.getBooks().stream().anyMatch(b -> b.getTitle().equals("Service Book 1"))
                                && a.getBooks().stream().anyMatch(b -> b.getTitle().equals("Service Book 2"))
                )
                .verifyComplete();
    }

    // ------------------ Additional CRUD Tests ------------------

    @Test
    void testCreateAuthor() {
        Author author = new Author();
        author.setName("New Author");

        StepVerifier.create(authorRepository.save(author))
                .expectNextMatches(saved -> saved.getId() != null && saved.getName().equals("New Author"))
                .verifyComplete();
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setName("Old Name");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMap(saved -> {
                                    saved.setName("Updated Name");
                                    return authorRepository.save(saved);
                                })
                )
                .expectNextMatches(updated -> updated.getName().equals("Updated Name"))
                .verifyComplete();
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setName("To be deleted");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMap(saved -> authorRepository.deleteById(saved.getId()).then(Mono.just(saved.getId())))
                                .flatMap(id -> authorRepository.findById(id))
                )
                .verifyComplete(); // should be empty
    }

    @Test
    void testFindAuthorById() {
        Author author = new Author();
        author.setName("Findable Author");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMap(saved -> authorRepository.findById(saved.getId()))
                )
                .expectNextMatches(found -> found.getName().equals("Findable Author"))
                .verifyComplete();
    }

    @Test
    void testCreateBooksByAuthorId() {
        Author author = new Author();
        author.setName("Author for Book Creation");

        Book b1 = new Book();
        b1.setTitle("Book 1");
        Book b2 = new Book();
        b2.setTitle("Book 2");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMapMany(savedAuthor -> {
                                    b1.setAuthorId(savedAuthor.getId());
                                    b2.setAuthorId(savedAuthor.getId());
                                    return bookRepository.saveAll(List.of(b1, b2));
                                })
                )
                .expectNextCount(2)
                .verifyComplete();
    }

    // ------------------ Update Book ------------------
    @Test
    void testUpdateBookByAuthorId() {
        Author author = new Author();
        author.setName("Author for Book Update");

        Book book = new Book();
        book.setTitle("Old Title");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMap(savedAuthor -> {
                                    book.setAuthorId(savedAuthor.getId());
                                    return bookRepository.save(book);
                                })
                                .flatMap(savedBook -> {
                                    savedBook.setTitle("Updated Title");
                                    return bookRepository.save(savedBook);
                                })
                )
                .expectNextMatches(updatedBook -> updatedBook.getTitle().equals("Updated Title"))
                .verifyComplete();
    }

    // ------------------ Delete Book ------------------
    @Test
    void testDeleteBookByAuthorId() {
        Author author = new Author();
        author.setName("Author for Book Deletion");

        Book book = new Book();
        book.setTitle("To Be Deleted");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMap(savedAuthor -> {
                                    book.setAuthorId(savedAuthor.getId());
                                    return bookRepository.save(book);
                                })
                                .flatMap(savedBook -> bookRepository.deleteById(savedBook.getId()).then(Mono.just(savedBook.getId())))
                                .flatMap(deletedId -> bookRepository.findById(deletedId))
                )
                .verifyComplete(); // should be empty after deletion
    }

    // ------------------ Find Books by Author ------------------
    @Test
    void testFindBooksByAuthorId() {
        Author author = new Author();
        author.setName("Author for Book Query");

        Book b1 = new Book();
        b1.setTitle("Book A");
        Book b2 = new Book();
        b2.setTitle("Book B");

        StepVerifier.create(
                        authorRepository.save(author)
                                .flatMapMany(savedAuthor -> {
                                    b1.setAuthorId(savedAuthor.getId());
                                    b2.setAuthorId(savedAuthor.getId());
                                    // ensure books are saved before querying
                                    return bookRepository.saveAll(List.of(b1, b2))
                                            .thenMany(bookRepository.findByAuthorId(savedAuthor.getId()));
                                })
                )
                .expectNextCount(2)
                .verifyComplete();
    }

}

