package com.jihun.booksearcher.book.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
    DESC("desc"),
    SUB_INFO("subInfo");

    private final String value;
}
