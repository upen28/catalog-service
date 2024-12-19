package com.polarbookshop.catalogservice.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.polarbookshop.catalogservice.web.BookController;

@WebMvcTest(BookController.class)
public class BookTest {

	@MockBean
	private BookService bookService;
	
	@Test
	public void testBook() {

	}

	@Test
	public void testBook2() {

	}

	@Test
	public void testBook3() {

	}

}
