package com.jihun.booksearcher.book.service;


import co.elastic.clients.elasticsearch.core.IndexResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BookServiceV2Impl implements BookServiceV2 {
    private final EsServiceImpl esService;


    @Override
    public IndexResponse upload() throws IOException {
        return esService.index();
    }
}
