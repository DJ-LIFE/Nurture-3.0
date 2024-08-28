package com.onlineBookStore.BookStoreApi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Book {
    private long id;
    private String title;
    private String author;
    private int price;
    private int isbn;
}
