package com.onlineBookStore.BookStoreApi.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.convert.DataSizeUnit;

@Getter
@Setter
@NoArgsConstructor
@Data
public class Book {
    private long id;
    @NotNull
    @Size(min = 2, max = 100)
    private String title;
    @NotNull
    @Size(min = 2, max = 50)
    private String author;
    @NotNull
    @Min(value = 0)
    private int price;
    private int isbn;
}
