package com.jihun.booksearcher.elasticSearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMaker {
    public static Query match(String keyword, String field, Operator op, float boost) {
        return MatchQuery.of(m -> m
                .query(keyword)
                .field(field)
                .operator(op)
                .boost(boost)
        )._toQuery();
    }

    public static Query match(String keyword, String field) {
        return MatchQuery.of(m -> m
                .query(keyword)
                .field(field)
        )._toQuery();
    }

    public static Query match(String keyword, String field, Operator op) {
        return MatchQuery.of(m -> m
                .query(keyword)
                .field(field)
                .operator(op)

        )._toQuery();
    }

    public static Query matchPhrase(String keyword, String field, float boost) {
        return MatchPhraseQuery.of(m -> m
                .query(keyword)
                .field(field)
                .boost(boost)
        )._toQuery();
    }

    public static Query matchPhrase(String keyword, String field) {
        return MatchPhraseQuery.of(m -> m
                .query(keyword)
                .field(field)
        )._toQuery();
    }
}
