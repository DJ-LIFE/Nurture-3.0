package com.library;

import com.library.service.BookService;

public class MainApp {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        bookService.performService();
    }
}
