//package com.jihun.booksearcher.elasitcSearch.dao;
//
//import com.jihun.booksearcher.elasitcSearch.EsConfig;
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
////	private final IndexCntService indexCntService;
//
//
//    /**
//     * 인덱싱 확인 후 데이터 삽입
//     */
//    @Override
//    public String bulkIndexing(String indexName, List<Map<String, Object>> list) {
//        RestHighLevelClient client = config.createConnection();
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        if (list == null || list.isEmpty()) {
//            log.error("Empty Data");
//            return "데이터가 존재 하지 않습니다.";
//        }
//
//        if (!isExistIndex(indexName)) {
//            boolean acknowledged = createIndex(indexName, client);
//
//            if (acknowledged) {
//                log.info(indexName + " success to create index");
//            } else {
//                log.info(indexName + " fail to create index");
//                return "인덱스 생성을 실패하였습니다.";
//            }
//        }
//        try {
//            log.info("indexing start");
//            bulkIndexing(indexName, list, client);
//        } catch (Exception e) {
//            log.info("인덱싱에 실패하였습니다.");
//            e.printStackTrace();
//            return "인덱싱에 실패하였습니다.";
//        }
//
//        config.closeConnection(client);
//        stopWatch.stop();
//        log.info("인덱싱에 완료했습니다.");
//        return "인덱싱을 완료했습니다.";
//    }
//
//    /**
//     * 인덱스 삭제
//     */
//    @Override
//    public String deleteIndex(String indexName) {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        if (isExistIndex(indexName)) {
//            if (!deleteIndexConnetion(indexName)) {
//                log.info(indexName + "fail to delete index");
//                return "인덱스 삭제에 실패하였습니다.";
//            }
//        }
//        stopWatch.stop();
//        log.info("인덱싱에 삭제에 성공하였습니다.");
//        return "인덱스 삭제에 성공하였습니다.";
//    }
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
//    /**
//     * 엘라스틱서치 인덱스 존재여부 확인
//     *
//     * @param indexName 인덱스 이름
//     * @return 인덱스 존재 여부
//     */
//    private boolean isExistIndex(String indexName) {
//        RestHighLevelClient client = null;
//        boolean acknowledged = false;
//        try {
//            client = config.createConnection();
//            GetIndexRequest request = new GetIndexRequest(indexName);
//            acknowledged = client.indices().exists(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        } finally {
//            config.closeConnection(client);
//        }
//
//        return acknowledged;
//    }
//
//    /**
//     * 인덱스 생성
//     *
//     * @param indexName
//     * @param client
//     * @return
//     */
//    private boolean createIndex(String indexName, RestHighLevelClient client) {
//        String doc = getDocuments(indexName, "all");
//
//        CreateIndexRequest request = new CreateIndexRequest(indexName);
//
//        request.source(doc, XContentType.JSON);
//        try {
//            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//            return createIndexResponse.isAcknowledged();
//        } catch (IOException e) {
//            log.error(e.toString());
//            return false;
//        }
//    }
//
//    /**
//     * 인덱스 이름으로 도큐먼트 조회
//     *
//     * @param indexName
//     * @param type
//     * @return
//     */
//    private String getDocuments(String indexName, String type) {
//        ByteArrayOutputStream result = new ByteArrayOutputStream();
//        try {
//            String path = IndexEnum.findPath(indexName, type);
//            InputStream in = this.getClass().getResourceAsStream(path);
//            byte[] buffer = new byte[1024];
//            int length;
//            if (nonNull(in)) {
//                while ((length = in.read(buffer)) != -1) {
//                    result.write(buffer, 0, length);
//                }
//            }
//        } catch (Exception e) {
//            log.error("Not found documents by indexName : " + indexName);
//        }
//
//        return result.toString();
//    }
//
//
//    /**
//     * 인덱스 삭제 연결
//     */
//    public boolean deleteIndexConnetion(String indexName) {
//        RestHighLevelClient client = null;
//        boolean acknowledged = false;
//        try {
//            client = config.createConnection();
//            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
//            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
//            acknowledged = response.isAcknowledged();
//        } catch (ElasticsearchException | IOException e) {
//            log.error(e.toString());
//        } finally {
//            config.closeConnection(client);
//        }
//
//        if (acknowledged) {
//            log.info(indexName + "success to delete index");
//        } else {
//            log.info(indexName + "fail to delete index");
//        }
//
//        return acknowledged;
//    }
//
//    /**
//     * 인덱싱 데이터 삽입
//     *
//     * @param indexName
//     * @param indexableData
//     * @param client
//     */
//    private void bulkIndexing(
//            String indexName,
//            List<Map<String, Object>> indexableData,
//            RestHighLevelClient client
//    ) {
//        BulkRequest bulkRequest = new BulkRequest();
//
//        int requestCnt = 0;
//
//        int maxSize = 100 * 1024 * 1024; // 190MB
//        long currentSize = 0; // 현재 bulkRequest 크기 추적
//
//        for (Map<String, Object> el : indexableData) {
////            bulkRequest.add(new IndexRequest(indexName).id("isbn").source(el, XContentType.JSON));
//            IndexRequest indexReq = new IndexRequest(indexName)
//                    .id((String) el.get("isbn"))
//                    .source(el, XContentType.JSON)
//                    .create(true);//생성 설정
//            BytesReference source = indexReq.source();
//            currentSize += source.length(); // IndexRequest의 크기 누적
//
//            bulkRequest.add(indexReq);
//
//            requestCnt++;
//            if (currentSize > maxSize) {
//                try {
//                    log.info("현재 인덱싱 완료 갯수" + requestCnt);
//                    BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//                    for (BulkItemResponse bulkItemResponse : bulkResponse) {
//                        if (bulkItemResponse.isFailed()) {
//                            BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
//                            System.out.println(failure.getIndex() + " - " + failure.getType() + " - " + failure.getId() + " / "
//                                    + failure.getMessage());
//                        }
//                    }
//                } catch (IOException e) {
//                    log.info("인덱싱에 실패하였습니다." + e.getMessage());
//                }
//                //초기화
//                bulkRequest = new BulkRequest();
//                currentSize = 0;
//            }
//        }
//        if (!bulkRequest.requests().isEmpty()) {
//            log.info("현재 인덱싱 완료 갯수" + requestCnt);
//            try {
//                BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//                for (BulkItemResponse bulkItemResponse : bulkResponse) {
//                    if (bulkItemResponse.isFailed()) {
//                        BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
//                        System.out.println(failure.getIndex() + " - " + failure.getType() + " - " + failure.getId() + " / "
//                                + failure.getMessage());
//                    }
//                }
//            } catch (IOException e) {
//                log.info("인덱싱에 실패하였습니다." + e.getMessage());
//            }
//        }
//    }
//}
