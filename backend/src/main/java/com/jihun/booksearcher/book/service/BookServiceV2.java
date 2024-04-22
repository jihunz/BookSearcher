package com.jihun.booksearcher.book.service;

import com.jihun.booksearcher.book.dto.UploadStatus;
import com.jihun.booksearcher.book.model.BookV2;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BookServiceV2 {
    UploadStatus execUpload(String dirPath) throws IOException;

}

