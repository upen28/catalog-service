package com.polarbookshop.catalogservice.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	void testBookByIsbn() throws Exception {
		Book book = new Book(null, "1234567890", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null,
				null, null, 1);
		Mockito.when(bookService.viewBookDetails("1234567890")).thenReturn(book);
		mockMvc.perform(get("/books/1234567890")).andExpect(status().isOk());
	}

	@Test
	void BookNotFoundException() throws Exception {
		Mockito.when(bookService.viewBookDetails("1234567890")).thenThrow(new BookNotFoundException("1234567890"));
		mockMvc.perform(get("/books/1234567890")).andExpect(status().isNotFound());
	}

	@Test
	void testBookAlreadyExistsException() throws Exception {
		Book book = new Book(null, "1234567890", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null,
				null, null, 1);
		ObjectMapper mapper = new ObjectMapper();
		String bookJson = mapper.writeValueAsString(book);
		Mockito.when(bookService.addBookToCatalog(Mockito.any(Book.class)))
				.thenThrow(new BookAlreadyExistsException("1234567890"));

		mockMvc.perform(post("/books").contentType("application/json").content(bookJson))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	void testGetAllBooks() throws Exception {
		Book book = new Book(null, "1234567890", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null,
				null, null, 1);
		Book book2 = new Book(null, "1234567891", "Kubernetes in Action", "Marko Luksa", 10.0, "Manning", null, null,
				null, null, 1);
		Mockito.when(bookService.viewBookList()).thenReturn(List.of(book, book2));
		mockMvc.perform(get("/books")).andExpect(status().isOk());
	}

	@Test
	void deleteByIsbn() throws Exception {
		Mockito.doNothing().when(bookService).removeBookFromCatalog("1234567890");
		mockMvc.perform(delete("/books/1234567890")).andExpect(status().isNoContent());
	}

	@Test
	void testEditBook() throws Exception {
		Book editedBook = new Book(null, "1234567890", "Thinking in Java Fourth Edition", "Bruce Eckel", 10.0,
				"Prentice Hall", null, null, null, null, 1);

		ObjectMapper mapper = new ObjectMapper();
		String editedBookJson = mapper.writeValueAsString(editedBook);

		Mockito.when(bookService.editBookDetails(Mockito.anyString(), Mockito.any(Book.class))).thenReturn(editedBook);

		mockMvc.perform(put("/books/1234567890").contentType("application/json").content(editedBookJson))
				.andExpect(status().isOk()).andExpect(content().json(editedBookJson));
	}

	@Test
	void testAddBook() throws Exception {
		Book book = new Book(null, "1234567890", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null,
				null, null, 1);
		ObjectMapper mapper = new ObjectMapper();
		String bookJson = mapper.writeValueAsString(book);
		Mockito.when(bookService.addBookToCatalog(Mockito.any(Book.class))).thenReturn(book);

		mockMvc.perform(post("/books").contentType("application/json").content(bookJson))
				.andExpect(status().isCreated());

	}
}
