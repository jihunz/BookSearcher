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

//TODO: 도서 대여 가능 정보 크롤링 흐름: 도서 클릭
//
// 1. 제목을 이용하여 https://www.u-library.kr/ input에서 검색 -> 보기 100개 클릭 -> 도서관명, 청구 기호, 대출 가능여부 크롤링
// 2. 테이블과 도서관 좌표 마커가 표시된 지도 페이지로 이동, 접속 위치 표시, 접속 위치 근거리 도서관 마커 색깔 구분 -> 행에 hover -> 도서관 마커 색깔 변경 -> 도서관 마커 클릭 / hover 시 도서 대여정보 표시