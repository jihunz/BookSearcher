package com.jihun.booksearcher.elasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import com.jihun.booksearcher.book.model.BookV2;
import com.jihun.booksearcher.elasticSearch.config.EsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EsServiceImpl {
    private final EsConfig esConfig;
    @Value("${elasticsearch.index}")
    private String idxName;
    @Value("${elasticsearch.settingMappingPath}")
    private String settingMappingPath;

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
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }

        return result;
    }



    // TODO: 인덱스가 없고 매핑이 없으면 실행
    public boolean addSettingMapping() throws IOException {
        try (InputStream json = Files.newInputStream(Paths.get(settingMappingPath))) {
            CreateIndexRequest req = new CreateIndexRequest.Builder()
                    .index(idxName)
                    .withJson(json)
                    .build();

            ElasticsearchClient client = esConfig.elasticsearchClient();
            return client.indices().create(req).acknowledged();
        }
    }

}




