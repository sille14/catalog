package com.example.catalog;

import com.example.catalog.data.domain.Book;
import com.example.catalog.data.repository.BookRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CatalogApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void contextLoads() {
	}

	@Test
	void findByIdReturnsBookWithMatchingIsbn() {
		Book book = new Book();
		book.setIsbn(9780134685991L);
		book.setTitle("Effective Java");
		book.setDescription("A guide to best practices in Java");
		book.setAuthor("Joshua Bloch");
		bookRepository.saveAndFlush(book);
		entityManager.clear();

		assertThat(bookRepository.findById(book.getIsbn()))
				.hasValueSatisfying(found -> {
					assertThat(found.getIsbn()).isEqualTo(book.getIsbn());
					assertThat(found.getTitle()).isEqualTo(book.getTitle());
					assertThat(found.getDescription()).isEqualTo(book.getDescription());
					assertThat(found.getAuthor()).isEqualTo(book.getAuthor());
				});
	}

	@Test
	void findByIdReturnsEmptyWhenIsbnDoesNotExist() {
		assertThat(bookRepository.findById(9780134685991L)).isEmpty();
	}

}
