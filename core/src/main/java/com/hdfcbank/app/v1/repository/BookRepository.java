package com.hdfcbank.app.v1.repository;


import com.hdfcbank.app.v1.entities.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
    Flux<Book> findByAuthorId(Long authorId);
    Mono<Void> deleteByAuthorId(Long authorId);
}


