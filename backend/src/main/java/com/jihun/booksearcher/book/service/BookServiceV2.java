package com.jihun.booksearcher.book.service;

import com.jihun.booksearcher.book.model.BookV2;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BookServiceV2 {

    String uploadByFolder(String dirPath) throws IOException;

    List<BookV2> upload(File file);
}
