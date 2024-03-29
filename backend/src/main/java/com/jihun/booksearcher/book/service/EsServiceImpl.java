package com.jihun.booksearcher.book.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.jihun.booksearcher.book.model.Product;
import com.jihun.booksearcher.elasitcSearch.EsConfigV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EsServiceImpl {
    private final EsConfigV2 esConfigV2;

    public IndexResponse index() throws IOException {
        ElasticsearchClient esClient = esConfigV2.elasticsearchClient();

        Product product = new Product(1111, "City bike", 9800);

        IndexResponse res = esClient.index(i -> i
                .index("products")
                .id(String.valueOf(product.getId()))
                .document(product)
        );

        return res;
    }
}
