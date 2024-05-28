package com.jihun.booksearcher.book.model;

import lombok.Data;

import java.util.List;

@Data
public class SynonymReq {
    private String id;
    private String synonyms;
}
