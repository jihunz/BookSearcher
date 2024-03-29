package com.jihun.booksearcher.book.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private double id;
    private String name;
    private int price;
}
