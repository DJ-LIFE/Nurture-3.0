package com.onlineBookStore.BookStoreApi.controller;

import com.onlineBookStore.BookStoreApi.model.Book;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<EntityModel<Book>>> getAllBooks() {
//        return books;
        List<EntityModel<Book>> bookResources = new ArrayList<>();
        for(Book book: books){
            EntityModel<Book> resource = EntityModel.of(book);

            // Self Link
            Link selfLink = linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel();
            resource.add(selfLink);
            Link allBooksLink = linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books");
            resource.add(allBooksLink);
            bookResources.add(resource);
        }
        return ResponseEntity.ok(bookResources);
    }

    // Creating a Book REST Api
    // Excercise 5 Customizing Response Status and Headers

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntityModel<Book>> createBook(@Validated @RequestBody Book book) {
//        books.add(book);
//        return ResponseEntity.status(HttpStatus.CREATED).body(book);
        book.setId(idCounter.incrementAndGet());
        books.add(book);
        EntityModel<Book> resource = EntityModel.of(book);
        Link selfLink = linkTo(methodOn(BookController.class).getbookById(book.getId())).withSelfRel();
        resource.add(selfLink);

        // Add link to all books
        Link allBooksLink = linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel();
        resource.add(allBooksLink);

        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<Book>> updateBook(@PathVariable int id,@Validated @RequestBody Book book) {
        Optional<Book> existingBookOpt = books.stream().filter(b -> b.getId() == id).findFirst();
        if(existingBookOpt.isPresent()){
            Book existingBook = existingBookOpt.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPrice(book.getPrice());
            existingBook.setIsbn(book.getIsbn());
            EntityModel<Book> resource = EntityModel.of(existingBook);

            Link selfLink = linkTo(methodOn(BookController.class).getbookById(existingBook.getId())).withSelfRel();
            resource.add(selfLink);

            // Add link to all books
            Link allBooksLink = linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books");
            resource.add(allBooksLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
//        books.set(id, book);
//        return book;
    }
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
//        books.remove(id);
        boolean removed = books.removeIf(book -> book.getId() == id);
        if(removed) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // Fetch Book by id
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<Book>> getbookById(@PathVariable int id) {
//        return books.get(id);
        Optional<Book> book = books.stream().filter( b -> b.getId() == id).findFirst();
//        return book.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        if(book.isPresent()) {
            EntityModel<Book> resource = EntityModel.of(book.get());
            // Self Link
            Link selfLink = linkTo(methodOn(BookController.class).getbookById(id)).withSelfRel();
            resource.add(selfLink);
            Link allBooksLink = linkTo(methodOn(BookController.class).getAllBooks()).withRel("all-books");
            resource.add(allBooksLink);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        final int SHAPE_SQUARE = 2;
        private int id;
        private String name;
        private String email;
    }

    // Processing Form Data
    @PostMapping("/register")
    public Customer registerCustomer(@ModelAttribute Customer customer) {
        return customer;
    }
    // Getting a ResponseEntity with Custom headers
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookWithHeaders(@PathVariable int id) {
        return ResponseEntity.ok()
                .header("Id not Found", "book not Found")
                .body(books.get(id));
    }
}
