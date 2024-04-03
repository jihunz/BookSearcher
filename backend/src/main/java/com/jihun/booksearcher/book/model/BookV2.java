package com.jihun.booksearcher.book.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookV2 {
    private Long id;
    private Long isbn;
    private String title;
    private String author;
    private String publisher;
    private String img;
    private String description;
    private Long kdc;
}
