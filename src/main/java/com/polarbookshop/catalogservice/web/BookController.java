package com.polarbookshop.catalogservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("books")
public class BookController {

	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public Iterable<Book> get() {
		log.info("fetching... the list of books in the catalog");
		return bookService.viewBookList();
	}

	@GetMapping("{isbn}")
	public Book getBookByIsbn(@PathVariable("isbn") String isbn) {
		log.info("fetching the book with ISBN {} from the catalog", isbn);
		return bookService.viewBookDetails(isbn);
	}

	@DeleteMapping("{isbn}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteByIsbn(@PathVariable("isbn") String isbn) {
		log.info("deleting book with ISBN {} from the catalog", isbn);
		bookService.removeBookFromCatalog(isbn);
	}

	@PutMapping
	public Book put(@PathVariable("isbn") String isbn, @Valid @RequestBody Book book) {
		log.info("updating book with ISBN {} from the catalog", isbn);
		return bookService.editBookDetails(isbn, book);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book post(@Valid @RequestBody Book book) {
		log.info("adding a new  book to the catalog with ISBN {}", book.getIsbn());
		return bookService.addBookToCatalog(book);
	}

}
