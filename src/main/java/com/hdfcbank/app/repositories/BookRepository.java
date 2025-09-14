package com.hdfcbank.app.repositories;


import com.hdfcbank.app.entities.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
    Flux<Book> findByAuthorId(Long authorId);
    Mono<Void> deleteByAuthorId(Long authorId);
}


