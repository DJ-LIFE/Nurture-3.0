package com.onlineBookStore.BookStoreApi.controller;

import com.onlineBookStore.BookStoreApi.model.Book;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private List<Book> books = new ArrayList<>();

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    // Creating a Book REST Api
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        books.add(book);
        return book;
    }
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book) {
        books.set(id, book);
        return book;
    }
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        books.remove(id);
    }
    // Fetch Book by id
    @GetMapping("/{id}")
    public Book getbookById(@PathVariable int id) {
        return books.get(id);
    }

    // filter Books by title and Author
    public List<Book> searchBooks(@RequestParam Optional<String> title, @RequestParam Optional<String> author) {
        return books.stream()
                .filter(book -> title.map(t -> book.getTitle().equalsIgnoreCase(true)))
                .filter(book -> author.map(a -> book.getAuthor().equalsIgnoreCase(true)))
                .toList();
    }

    @Data
    public class Book {
        private int id;
        private String title;
        private String author;
        private double price;
        private String isbn;
    }

    // Excercise 4 Processing Request Body and Form Data
    // Processing JSOn Request Body
    @RestController
    @RequestMapping("/customers")
    public class CustomerController {
        @PostMapping
        public Customer createCustomer(@RequestBody Customer customer) {
            return customer;
        }
    }
    @Data
    public class Customer {
        private int id;
        private String name;
        private String email;
    }

    // Processing Form Data
    @PostMapping("/register")
    public Customer registerCustomer(@ModelAttribute Customer Customer customer) {
        return customer;
    }
}
