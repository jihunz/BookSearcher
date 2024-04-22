package com.jihun.booksearcher.book.service;

import com.jihun.booksearcher.book.util.UploadStatus;

import java.io.IOException;

public interface BookService {
    UploadStatus execUpload(String dirPath) throws IOException;
}

