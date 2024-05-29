package com.jihun.booksearcher.elasticSearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.synonyms.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SynonymServiceImpl {

    private final ElasticsearchClient client;

    public Result update(String id, List<String> request) throws IOException {
        List<SynonymRule> ruleList = request.stream()
                .map(v -> SynonymRule.of(r -> r.synonyms(v)))
                .toList();
        PutSynonymRequest putRequest = PutSynonymRequest.of(r -> r
                .id(id)
                .synonymsSet(ruleList)
        );

        PutSynonymResponse response = client.synonyms().putSynonym(putRequest);
        return response.result();
    }

    public DeleteSynonymResponse delete(String id) throws IOException {
        DeleteSynonymRequest deleteRequest = DeleteSynonymRequest.of(r -> r.id(id));
        return client.synonyms().deleteSynonym(deleteRequest);
    }
}
