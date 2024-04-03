package com.jihun.booksearcher.book.util.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class Convert2Long extends AbstractBeanField<Long, Long> {
    @Override
    protected Long convert(String str) throws CsvDataTypeMismatchException {
        try {
            if (str.isEmpty()) return null;

            long l = Long.parseLong(str.trim());
            return l;
        } catch (NumberFormatException e) {
            throw new CsvDataTypeMismatchException("[OpenCsv]: Cannot convert value to Long", e.getClass());
        }
    }
}
