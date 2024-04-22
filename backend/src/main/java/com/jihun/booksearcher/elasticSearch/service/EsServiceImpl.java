package com.jihun.booksearcher.elasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.*;
import com.jihun.booksearcher.book.model.Book;
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
    private final ElasticsearchClient client;

    @Value("${elasticsearch.settingMappingPath}")
    private String SETTING_MAPPING_PATH;

    public BulkResponse bulkIdx(List<Book> list, String idxName) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Book item : list) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(idxName)
                            .id(String.valueOf(item.getId()))
                            .document(item)
                    )
            );
        }

        BulkResponse result = client.bulk(br.build());

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

    public void createIdxAndSettingMapping(String idxName) throws IOException {
        try (InputStream json = Files.newInputStream(Paths.get(SETTING_MAPPING_PATH))) {
            CreateIndexRequest req = new CreateIndexRequest.Builder()
                    .index(idxName)
                    .withJson(json)
                    .build();

            boolean res = client.indices().create(req).acknowledged();
            log.info("[create index, setting, mapping]: {}", res);
        }
    }

    public boolean doesIndexExist(String idxName) throws IOException {
        ExistsRequest req = new ExistsRequest.Builder()
                .index(idxName)
                .build();
        return client.indices().exists(req).value();
    }

}




