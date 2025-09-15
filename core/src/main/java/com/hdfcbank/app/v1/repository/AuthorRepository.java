package com.hdfcbank.app.v1.repository;


import com.hdfcbank.app.v1.entities.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {}
