package com.jihun.booksearcher.book.service;

import co.elastic.clients.elasticsearch.core.IndexResponse;

import java.io.IOException;

public interface BookServiceV2 {
    IndexResponse upload() throws IOException;
}
