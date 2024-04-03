package com.jihun.booksearcher.book.util.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class Convert2Double extends AbstractBeanField<Double, Double> {
    @Override
    protected Double convert(String str) throws CsvDataTypeMismatchException {
        try {
            if (str.isEmpty()) return null;

            double l = Double.parseDouble(str.trim());
            return l;
        } catch (NumberFormatException e) {
            throw new CsvDataTypeMismatchException("[OpenCsv]: Cannot convert value to Double", e.getClass());
        }
    }
}
