package com.jihun.booksearcher.book.model;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.opencsv.bean.CsvBindByName;
import io.micrometer.common.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
public class Book {
    private long  id;
    private float score;
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

    public Book(Hit<Book> hit) {
        Book source = hit.source();

        this.setId(source.getId());
        this.setScore(Float.parseFloat(String.valueOf(hit.score())));
        this.setIsbn(source.getIsbn());
        this.setTitle(trimField(source.getTitle()));
        this.setAuthor(trimField(source.getAuthor()));
        this.setPublisher(trimField(source.getPublisher()));
        this.setImg(trimField(source.getImg()));
        this.setDescription(trimField(source.getDescription()));
        String kdc = trimField(source.getKdc());
        if (StringUtils.isNotBlank(kdc)) {
            this.setKdc(kdc);
        }
    }

    public static String trimField(String str) {
        if (isNull(str)) {
            return null;
        }

        return str.trim();
    }
}
