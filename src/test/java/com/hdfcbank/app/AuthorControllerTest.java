package com.hdfcbank.app;

import com.hdfcbank.app.controllers.AuthorController;
import com.hdfcbank.app.entities.Author;
import com.hdfcbank.app.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("qa")
class AuthorControllerTest {

    private static final String BASE_URI = "/api/authors";

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToController(authorController)
                .configureClient()
                .baseUrl("/")   // base "/" is fine; URIs below start with /api/authors
                .build();
    }

    @Test
    void testGetAllAuthors() {
        when(authorService.getAllAuthors())
                .thenReturn(Flux.just(new Author(1L, "John Doe", Collections.emptyList())));

        webTestClient.get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("John Doe");

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void testGetAuthorWithBooks() {
        Author author = new Author(1L, "Jane Smith", Collections.emptyList());
        when(authorService.findAuthorWithBooks(1L)).thenReturn(Mono.just(author));

        webTestClient.get()
                .uri(BASE_URI + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Jane Smith");

        verify(authorService, times(1)).findAuthorWithBooks(1L);
    }

    @Test
    void testCreateAuthor() {
        Author author = new Author(null, "New Author", Collections.emptyList());
        Author saved = new Author(1L, "New Author", Collections.emptyList());

        when(authorService.createAuthor(any(Author.class))).thenReturn(Mono.just(saved));

        webTestClient.post()
                .uri(BASE_URI)
                .bodyValue(author)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("New Author");

        verify(authorService, times(1)).createAuthor(any(Author.class));
    }

    @Test
    void testDeleteAuthor() {
        when(authorService.deleteAuthor(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(BASE_URI + "/1")
                .exchange()
                .expectStatus().isNoContent();

        verify(authorService, times(1)).deleteAuthor(1L);
    }
}
