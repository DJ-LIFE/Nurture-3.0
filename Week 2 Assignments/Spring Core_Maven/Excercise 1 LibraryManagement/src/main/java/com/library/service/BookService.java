package com.library.service;
import com.library.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
    }

    public void performService() {
        System.out.println("BookService is performing service");
        bookRepository.performRepositoryAction();
    }

}
