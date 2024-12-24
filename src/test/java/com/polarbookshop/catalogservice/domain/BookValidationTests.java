package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class BookValidationTests {

	private static Validator validator;

	@Test
	void testIsbnValadationEqualTo10() {
		Book book = new Book(null, "1234567890", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null,
				null, null, 1);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).isEmpty();
	}

	@Test
	void testIsbnValadationEqualTo13() {
		Book book = new Book(null, "1234567890123", "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null,
				null, null, null, 1);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).isEmpty();
	}

	@ParameterizedTest
	@MethodSource("isbnValidationTestCases")
	void testIsbnValidation(String isbn, boolean expected) {
		Book book = new Book(null, isbn, "Thinking in Java", "Bruce Eckel", 10.0, "Prentice Hall", null, null, null,
				null, 1);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		if (isbn.equals("")) {
			assertThat(violations).hasSize(expected ? 0 : 2);
		} else {
			assertThat(violations).hasSize(expected ? 0 : 1);
		}

	}

	private static List<Object[]> isbnValidationTestCases() {
		return Arrays.asList(
				new Object[] { "1234567890123", true },
				new Object[] { "12345678901234", false },
				new Object[] { "1234567890", true },
				new Object[] { "", false });
	}

	@Test
	void testTitleValidation() {
		Book book = new Book(null, "1234567890", "", "Bruce Eckel", 10.0, "Prentice Hall", null, null, null, null, 1);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).isNotEmpty();
	}

	@Test
	void testAuthorValidation() {
		Book book = new Book(null, "1234567890", "Thinking in Java", "", 10.0, "Prentice Hall", null, null, null, null,
				1);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).isNotEmpty();
	}

	@Test
	void testPriceValidation() {
		Book book = new Book(null, "1234567890", "Thinking in Java", "Bruce Eckel", -10.0, "Prentice Hall", null, null,
				null, null, 1);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).isNotEmpty();
	}

}
