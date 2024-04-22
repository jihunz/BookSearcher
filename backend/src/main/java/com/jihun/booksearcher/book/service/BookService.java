package com.jihun.booksearcher.book.service;

import com.jihun.booksearcher.book.dto.UploadStatus;

import java.io.IOException;

public interface BookService {
    UploadStatus execUpload(String dirPath) throws IOException;

}

