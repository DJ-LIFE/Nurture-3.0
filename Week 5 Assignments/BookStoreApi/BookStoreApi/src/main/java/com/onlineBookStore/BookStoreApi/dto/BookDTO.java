package com.onlineBookStore.BookStoreApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookDTO {
    @JsonProperty("book_title")
    private String title;
    @JsonProperty("book_author")
    private String author;
    @JsonProperty("book_price")
    private double price;
}
