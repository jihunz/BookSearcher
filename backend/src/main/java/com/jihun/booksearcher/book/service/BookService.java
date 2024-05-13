package com.jihun.booksearcher.book.service;

import com.jihun.booksearcher.book.model.Book;
import com.jihun.booksearcher.book.util.UploadStatus;

import java.io.IOException;
import java.util.List;

public interface BookService {
    UploadStatus execUpload(String dirPath) throws IOException;

    List<Book> searchTitleDesc(String keyword) throws IOException;

    List<Book> searchSubInfo(String keyword) throws IOException;
}

