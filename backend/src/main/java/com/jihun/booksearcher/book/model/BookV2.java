package com.jihun.booksearcher.book.model;

import com.jihun.booksearcher.book.util.csv.Convert2Double;
import com.jihun.booksearcher.book.util.csv.Convert2Long;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookV2 {
    private Long id;
    @CsvCustomBindByName(column = "ISBN_THIRTEEN_NO", converter = Convert2Long.class)
    private Long isbn;
    @CsvBindByName(column = "TITLE_NM")
    private String title;
    @CsvBindByName(column = "AUTHR_NM")
    private String author;
    @CsvBindByName(column = "PUBLISHER_NM")
    private String publisher;
    @CsvBindByName(column = "IMAGE_URL")
    private String img;
    @CsvBindByName(column = "BOOK_INTRCN_CN")
    private String description;
    @CsvCustomBindByName(column = "KDC_NM", converter = Convert2Double.class)
    private Double kdc;
}
