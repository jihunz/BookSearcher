package com.jihun.booksearcher.book.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.jihun.booksearcher.book.model.Product;
import com.jihun.booksearcher.elasitcSearch.EsConfigV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class EsServiceImpl {
    private final EsConfigV2 esConfigV2;

    public IndexResponse index() throws IOException {
        ElasticsearchClient esClient = esConfigV2.elasticsearchClient();

        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Product(i , "product" + i, 1000 * i));
        }

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Product item : list) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("products")
                            .id(String.valueOf(item.getId()))
                            .document(item)
                    )
            );
        }

        BulkResponse result = esClient.bulk(br.build());

        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item: result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }

        return null;
    }
}
