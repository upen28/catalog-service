package com.polarbookshop.catalogservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@Testcontainers
class BookControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private BookService bookService;

    @Test
    void testBookByIsbn() {
        Book book = new Book(null, "1234567891", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null,
                null, null, 1);
        bookService.addBookToCatalog(book);
        webTestClient.get().uri("/books/1234567891").exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.isbn").isEqualTo("1234567891");
        bookService.removeBookFromCatalog("1234567891");
    }

    @Test
    void BookNotFoundException() {
        webTestClient.get().uri("/books/1234567899").exchange().expectStatus().isNotFound();
    }

    @Test
    void testBookAlreadyExistsException() {
        Book book = new Book(null, "1234567892", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null,
                null, null, 1);
        bookService.addBookToCatalog(book);
        webTestClient.post().uri("/books").bodyValue(book).exchange().expectStatus().is4xxClientError();
        bookService.removeBookFromCatalog("1234567892");
    }

    @Test
    void testGetAllBooks() {
        webTestClient.get().uri("/books").exchange().expectStatus().isOk();
    }

    @Test
    void deleteByIsbn() {
        Book deletedBook = new Book(null, "1234567893", "Cloud Native Java", "Thomas Vitale", 120.0, "Manning", null,
                null,
                null, null, 1);
        bookService.addBookToCatalog(deletedBook);
        webTestClient.delete().uri("/books/1234567893").exchange().expectStatus().isNoContent();
    }

    @Test
    void testEditBook() {
        Book editedBook = new Book(null, "1234567894", "Thinking in Java Fourth Edition", "Bruce Eckel", 10.0,
                "Prentice Hall", null, null,
                null, null, 1);
        webTestClient.put().uri("/books/1234567894").bodyValue(editedBook).exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.title").isEqualTo("Thinking in Java Fourth Edition");
    }

    @Test
    void testAddBook() {
        Book newBook = new Book(null, "1234567895", "Kubernetes in Action", "Marko Luksa", 110.0, "Manning", null, null,
                null, null, 1);
        webTestClient.post().uri("/books").bodyValue(newBook).exchange().expectStatus().isCreated();
        bookService.removeBookFromCatalog("1234567895");
    }
}
