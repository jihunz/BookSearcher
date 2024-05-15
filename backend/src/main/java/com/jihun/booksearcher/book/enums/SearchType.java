package com.jihun.booksearcher.book.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
    DESC("desc"),
    SUB_INFO("subInfo");

    private final String value;

    public static SearchType findByVal(String value) {
        for (SearchType item : SearchType.values()) {
            if (item.getValue().equals(value)) return item;
        }
        return null;
    }

}
