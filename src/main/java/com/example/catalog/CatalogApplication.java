package com.example.catalog;

import com.example.catalog.data.domain.Book;
import com.example.catalog.data.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

	@Bean
	CommandLineRunner loadTestData(BookRepository repository) {
		return args -> {
			seedBook(repository, 9780747532699L, "Harry Potter and the Philosopher's Stone",
					"Harry discovers he is a wizard and begins his first year at Hogwarts.");
			seedBook(repository, 9780747538493L, "Harry Potter and the Chamber of Secrets",
					"Harry investigates mysterious attacks during his second year at Hogwarts.");
			seedBook(repository, 9780747542155L, "Harry Potter and the Prisoner of Azkaban",
					"An escaped prisoner leads Harry to uncover secrets about his family.");
			seedBook(repository, 9780747546245L, "Harry Potter and the Goblet of Fire",
					"Harry unexpectedly competes in the dangerous Triwizard Tournament.");
			seedBook(repository, 9780747551003L, "Harry Potter and the Order of the Phoenix",
					"Harry and his friends form a secret group to practice defensive magic.");
			seedBook(repository, 9780747581086L, "Harry Potter and the Half-Blood Prince",
					"Harry learns about Voldemort's past and the key to defeating him.");
			seedBook(repository, 9780747591054L, "Harry Potter and the Deathly Hallows",
					"Harry, Ron, and Hermione search for Horcruxes as the final battle approaches.");
		};
	}

	private void seedBook(BookRepository repository, long isbn, String title, String description) {
		if (repository.existsById(isbn)) {
			return;
		}
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setDescription(description);
		book.setAuthor("J. K. Rowling");
		repository.save(book);
	}

}
