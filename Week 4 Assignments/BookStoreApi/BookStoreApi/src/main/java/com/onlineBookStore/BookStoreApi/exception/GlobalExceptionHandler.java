package com.onlineBookStore.BookStoreApi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BootstrapMethodError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBookNotFoundException(BookNotFoundException ex) {
        return ex.getMessage();
    }

    public class BookNotFoundException extends RuntimeException {
        public BookNotFoundException(String message) {
            super(message);
        }
    }
}
