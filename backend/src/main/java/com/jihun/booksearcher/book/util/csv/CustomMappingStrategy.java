package com.jihun.booksearcher.book.util.csv;

import com.jihun.booksearcher.book.model.BookV2;
import com.opencsv.bean.MappingStrategy;

public class CustomMappingStrategy<T> implements MappingStrategy<BookV2> {

    @Override
    protected BookV2 processResult(String[] line) {
        // 특정 칼럼 값이 null이면 해당 행을 건너뜁니다.
        if (line[0] == null || line[0].isEmpty()) {
            return null;
        }

        // BookV2 객체를 생성하고 반환합니다.
        BookV2 bean = new BookV2();
        bean.setColumn1(line[0]);
        bean.setColumn2(line[1]);
        return bean;
    }
}
