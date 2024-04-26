package com.jihun.booksearcher.elasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import com.jihun.booksearcher.book.model.Book;
import com.jihun.booksearcher.elasticSearch.config.EsConfig;
import com.jihun.booksearcher.elasticSearch.util.QueryMaker;
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

    // must + m -> 단어 반드시 포함
    public List<Hit<Book>> descMustQuery(String keyword) throws IOException {
        Query matchByDesc = MatchQuery.of(m -> m
                .field("description")
                .query(keyword)
        )._toQuery();

        SearchResponse<Book> response = client.search(s -> s
                        .index("book")
                        .query(q -> q
                                .bool(b -> b
                                        .must(matchByDesc)
                                )
                        ),
                Book.class
        );

        return response.hits().hits();
    }

    // should + mp, m -> 구 포함하면 가중, 단어 포함, 단어 하나라도 포함
    public List<Hit<Book>> titleDescShouldQuery(String keyword) throws IOException {
        Query matchTitle = QueryMaker.match(keyword, "title");
        Query matchDesc = QueryMaker.match(keyword, "description");
        Query matchPhraseTitle = QueryMaker.matchPhrase(keyword, "title", 2F);
        Query matchPhraseDesc = QueryMaker.matchPhrase(keyword, "description", 2F);
//        Query matchSubInfoText = QueryMaker.match(keyword, "subInfoText");
//        Query matchPhraseSubInfoText = QueryMaker.matchPhrase(keyword, "subInfoText");

        SearchResponse<Book> response = client.search(s -> s
                        .index("book")
                        .query(q -> q
                                .bool(b -> b
                                        .should(matchTitle)
                                        .should(matchDesc)
                                        .should(matchPhraseTitle)
                                        .should(matchPhraseDesc)
//                                        .should(matchSubInfoText)
//                                        .should(matchPhraseSubInfoText)
                                )
                        ),
                Book.class
        );

        return response.hits().hits();
    }
}




