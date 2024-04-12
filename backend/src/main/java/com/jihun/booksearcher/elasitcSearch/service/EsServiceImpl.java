package com.jihun.booksearcher.elasitcSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.jihun.booksearcher.book.model.BookV2;
import com.jihun.booksearcher.elasitcSearch.config.EsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class EsServiceImpl {
    private final EsConfig esConfig;

    public BulkResponse bulkIdx(List<BookV2> list) throws IOException {
        ElasticsearchClient esClient = esConfig.elasticsearchClient();

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (BookV2 item : list) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("book")
                            .id(String.valueOf(item.getId()))
                            .document(item)
                    )
            );
        }

        BulkResponse result = esClient.bulk(br.build());

        if (result.errors()) {
            log.error("[EsServiceImpl-index]: Bulk had errors");
            for (BulkResponseItem item: result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }

        return result;
    }
}
