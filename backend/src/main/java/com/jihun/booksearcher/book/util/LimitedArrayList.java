package com.jihun.booksearcher.book.util;

import java.util.ArrayList;

public class LimitedArrayList<E> extends ArrayList<E> {
    private final int maxSize;

    public LimitedArrayList(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(E e) {
        if (size() < maxSize) {
            return super.add(e);
        }
        return false;
    }
}
