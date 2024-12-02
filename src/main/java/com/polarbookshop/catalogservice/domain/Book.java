package com.polarbookshop.catalogservice.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Long id;

	@NotBlank(message = "The book ISBN must be defined.")
	@Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
	String isbn;

	@NotBlank(message = "The book title must be defined.")
	String title;

	@NotBlank(message = "The book author must be defined.")
	String author;

	@NotNull(message = "The book price must be defined.")
	@Positive(message = "The book price must be greater than zero.")
	Double price;

	String publisher;

	@CreatedDate
	Instant createdDate;

	@LastModifiedDate
	Instant lastModifiedDate;

	@CreatedBy
	String createdBy;

	@LastModifiedBy
	String lastModifiedBy;

	@Version
	int version;

	public Book() {

	}

	public Book(Long id, String isbn, String title, String author, Double price, String publisher, Instant createdDate,
			Instant lastModifiedDate, String createdBy, String lastModifiedBy, int version) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.publisher = publisher;
	}

	public static Book of(String isbn, String title, String author, Double price, String publisher) {
		return new Book(null, isbn, title, author, price, publisher, null, null, null, null, 0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
}
