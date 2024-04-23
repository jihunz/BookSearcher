package com.jihun.booksearcher.book.service;

import com.jihun.booksearcher.book.model.Book;
import com.jihun.booksearcher.book.util.UploadStatus;

import java.io.IOException;

public interface BookService {
    UploadStatus execUpload(String dirPath) throws IOException;

    Book search(String keyword);
}

