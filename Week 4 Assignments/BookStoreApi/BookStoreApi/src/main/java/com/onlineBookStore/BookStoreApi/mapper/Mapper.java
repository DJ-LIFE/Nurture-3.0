package com.onlineBookStore.BookStoreApi.mapper;


import com.onlineBookStore.BookStoreApi.controller.BookController;
import com.onlineBookStore.BookStoreApi.dto.BookDTO;
import com.onlineBookStore.BookStoreApi.model.Book;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public bookDTO toDto(BookController.Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    public Book toEntity(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }
}
