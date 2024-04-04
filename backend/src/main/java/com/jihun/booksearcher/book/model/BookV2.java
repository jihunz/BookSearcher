package com.jihun.booksearcher.book.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookV2 {
    private long id;
    @CsvBindByName(column = "ISBN_THIRTEEN_NO")
    private String isbn;
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
    @CsvBindByName(column = "KDC_NM")
    private String kdc;
}
