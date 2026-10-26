package com.example.catalog.controller;

import com.example.catalog.data.domain.Book;
import com.example.catalog.data.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    public List<Book> getBooks(@RequestParam(name = "query", required = false) String query) {
        return bookRepository.search(query);
    }

    @GetMapping("/{isbn}")
    public Optional<Book> getBook(@PathVariable("isbn") String isbn) {
        return bookRepository.findById(isbn);
    }

    @PostMapping(consumes = "application/json")
    public void postBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable("isbn") String isbn) {
        bookRepository.deleteById(isbn);
    }
}
