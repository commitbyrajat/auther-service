package com.hdfcbank.app.service;


import com.hdfcbank.app.entities.Author;
import com.hdfcbank.app.entities.Book;
import com.hdfcbank.app.repositories.AuthorRepository;
import com.hdfcbank.app.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }


    public Mono<Author> findAuthorWithBooks(Long authorId) {
        return authorRepository.findById(authorId)
                .flatMap(author ->
                        bookRepository.findByAuthorId(authorId)
                                .collectList()
                                .map(books -> {
                                    author.setBooks(books);
                                    return author;
                                }));
    }

    public Mono<Author> createAuthor(Author author) {
        return authorRepository.save(author)
                .flatMap(savedAuthor ->
                        bookRepository.saveAll(author.getBooks())
                                .collectList()
                                .map(savedBooks -> {
                                    savedAuthor.setBooks(savedBooks);  // set books
                                    return savedAuthor;               // return saved author
                                })
                );
    }

    public Mono<Author> updateAuthor(Long id, Author author) {
        return authorRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(author.getName());
                    return authorRepository.save(existing);
                });
    }

    public Mono<Void> deleteAuthor(Long id) {
        return bookRepository.deleteByAuthorId(id)
                .then(authorRepository.deleteById(id));
    }

    // ------------------ Book CRUD ------------------

    public Flux<Book> findBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public Mono<Book> addBookToAuthor(Long authorId, Book book) {
        book.setAuthorId(authorId);
        return bookRepository.save(book);
    }

    public Mono<Book> updateBookByAuthor(Long authorId, Long bookId, Book book) {
        return bookRepository.findById(bookId)
                .filter(b -> b.getAuthorId().equals(authorId))
                .flatMap(existing -> {
                    existing.setTitle(book.getTitle());
                    return bookRepository.save(existing);
                });
    }

    public Mono<Void> deleteBookByAuthor(Long authorId, Long bookId) {
        return bookRepository.findById(bookId)
                .filter(b -> b.getAuthorId().equals(authorId))
                .flatMap(bookRepository::delete);
    }
}

