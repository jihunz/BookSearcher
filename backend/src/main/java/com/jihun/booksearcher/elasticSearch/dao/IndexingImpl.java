//package com.jihun.booksearcher.elasitcSearch.dao;
//
//import com.jihun.booksearcher.elasitcSearch.config.EsConfig;
//import com.jihun.booksearcher.elasitcSearch.IndexEnum;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.elasticsearch.ElasticsearchException;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkItemResponse;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.common.StopWatch;
//import org.elasticsearch.common.bytes.BytesReference;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.Operator;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static java.util.Objects.nonNull;
//
//@Log4j2
//@RequiredArgsConstructor
//@Service
//public class IndexingImpl<T> implements Indexing<T> {
//    private final RestHighLevelClient elasticsearchClient;
//    private final EsConfig config;
//
//    @Override
//    public Map<String, SearchResponse> search(String keyword) throws IOException {
//        Map<String, SearchResponse> resultMap = new HashMap<>();
//        int defaultResultSize = 10;
//
//        SearchResponse result = invokeSearch(descMustQuery(keyword), defaultResultSize);
//        resultMap.put("result1", result);
//
//        int resultSize = result.getHits().getHits().length;
//        if (resultSize <= 2) {
//            result = invokeSearch(allShouldQuery(keyword), defaultResultSize - resultSize);
//            resultMap.put("result2", result);
//        }
//
//        return resultMap;
//    }
//
//    private static BoolQueryBuilder descMustQuery(String keyword) {
//        return QueryBuilders.boolQuery()
//                .must(QueryBuilders.matchPhraseQuery("description", keyword));
//    }
//
//    private static BoolQueryBuilder allShouldQuery(String keyword) {
//        return QueryBuilders.boolQuery()
//                .should(QueryBuilders.matchQuery("title", keyword).operator(Operator.AND).boost(2))
//                .should(QueryBuilders.matchQuery("description", keyword).operator(Operator.AND).boost(4))
//                .should(QueryBuilders.matchPhraseQuery("title", keyword).boost(3))
//                .should(QueryBuilders.matchPhraseQuery("description", keyword).boost(5))
//                .should(QueryBuilders.matchQuery("subInfoText", keyword))
//                .should(QueryBuilders.matchPhraseQuery("subInfoText", keyword));
//    }
//
//    private SearchResponse invokeSearch(BoolQueryBuilder boolQueryBuilder, int size) throws IOException {
//        return elasticsearchClient.search(createSearchReq(boolQueryBuilder, size), RequestOptions.DEFAULT);
//    }
//
//    private static SearchRequest createSearchReq(BoolQueryBuilder boolQueryBuilder, int size) {
//        SearchRequest searchRequest = new SearchRequest("book");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        sourceBuilder.query(boolQueryBuilder);
//        sourceBuilder.size(size);
//
//        searchRequest.source(sourceBuilder);
//        return searchRequest;
//    }
//
//}
