package com.jihun.booksearcher.elasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import com.jihun.booksearcher.book.model.BookV2;
import com.jihun.booksearcher.elasticSearch.config.EsConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${elasticsearch.index}")
    private String idxName;
    @Value("${elasticsearch.settingMappingPath}")
    private String settingMappingPath;

    public BulkResponse bulkIdx(List<BookV2> list) throws IOException {
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

    // TODO: 인덱스가 없고 매핑이 없으면 실행
    public boolean addSettingMapping() throws IOException {
        try (InputStream json = Files.newInputStream(Paths.get(settingMappingPath))) {
            CreateIndexRequest req = new CreateIndexRequest.Builder()
                    .index(idxName)
                    .withJson(json)
                    .build();

            boolean res = client.indices().create(req).acknowledged();
            log.info("[create setting and mapping]: {}", res);
            return res;
        }
    }

//    private boolean doesIndexExist(String indexName) throws IOException {
//        GetIndexRequest request = new GetIndexRequest(indexName);
//        return client.indices().exists(request, RequestOptions.DEFAULT);
//    }

}




