package com.hdfcbank.app.repositories;


import com.hdfcbank.app.entities.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {}
