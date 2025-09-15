package com.hdfcbank.app.controller;


import com.hdfcbank.app.v1.entities.Author;
import com.hdfcbank.app.v1.entities.Book;
import com.hdfcbank.app.v1.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    // Constructor injection instead of @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    // Get all authors
    @GetMapping
    public Flux<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // Get author with books
    @GetMapping("/{id}")
    public Mono<Author> getAuthor(@PathVariable("id") Long id) {
        return authorService.findAuthorWithBooks(id);
    }

    // Create author
    @PostMapping
    public Mono<Author> createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    // Update author
    @PutMapping("/{id}")
    public Mono<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }

    // Delete author
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAuthor(@PathVariable("id") Long id) {
        return authorService.deleteAuthor(id);
    }

    // Add a book to an author
    @PostMapping("/{id}/books")
    public Mono<Book> addBook(@PathVariable Long id, @RequestBody Book book) {
        return authorService.addBookToAuthor(id, book);
    }

    // Update a book by author
    @PutMapping("/{authorId}/books/{bookId}")
    public Mono<Book> updateBook(@PathVariable Long authorId,
                                 @PathVariable Long bookId,
                                 @RequestBody Book book) {
        return authorService.updateBookByAuthor(authorId, bookId, book);
    }

    // Delete a book by author
    @DeleteMapping("/{authorId}/books/{bookId}")
    public Mono<Void> deleteBook(@PathVariable Long authorId,
                                 @PathVariable Long bookId) {
        return authorService.deleteBookByAuthor(authorId, bookId);
    }

    // Get all books for an author
    @GetMapping("/{id}/books")
    public Flux<Book> getBooks(@PathVariable Long id) {
        return authorService.findBooksByAuthorId(id);
    }
}
