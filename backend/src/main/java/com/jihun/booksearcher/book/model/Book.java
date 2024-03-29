//package com.jihun.booksearcher.book.model;
//
//
//import lombok.Data;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.elasticsearch.search.SearchHit;
//
//import java.util.Map;
//
//import static java.util.Objects.isNull;
//
//@Data
//public class Book {
//
//    private Double score;
//
//    private Long isbn;
//
//    private String title;
//
//    private String author;
//
//    private String publisher;
//
//    private String image;
//
//    private String description;
//
//    private Double kdc;
//
//
//
//    public Book(SearchHit hit) {
//        String isbnVal = "";
//        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//        if (sourceAsMap.get("isbn") != null) {
//            isbnVal = "isbn";
//        } else if (sourceAsMap.get("isbn13") != null) {
//            isbnVal = "isbn13";
//        }
//
//        this.setScore(Double.parseDouble(String.valueOf(hit.getScore())));
//        this.setIsbn(Long.parseLong(String.valueOf(sourceAsMap.get(isbnVal))));
//        this.setTitle(trimField(String.valueOf(sourceAsMap.get("title"))));
//        this.setAuthor(trimField(String.valueOf(sourceAsMap.get("author"))));
//        this.setPublisher(trimField(String.valueOf(sourceAsMap.get("publisher"))));
//        this.setImage(trimField(String.valueOf(sourceAsMap.get("image"))));
//        this.setDescription(trimField(String.valueOf(sourceAsMap.get("description"))));
//        String kdc = trimField(String.valueOf(sourceAsMap.get("kdc")));
//        if (kdc != null && StringUtils.isNotBlank(kdc) && NumberUtils.isNumber(kdc)) {
//            this.setKdc(Double.parseDouble(kdc));
//        }
//    }
//
//    public static String trimField(String str) {
//        if (isNull(str)) {
//            return null;
//        }
//
//        return str.trim();
//    }
//}