//package com.jihun.booksearcher.book.service;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import com.jihun.booksearcher.book.model.Book;
//import com.jihun.booksearcher.elasitcSearch.EsClient;
//import com.jihun.booksearcher.elasitcSearch.config.EsConfig;
//import com.jihun.booksearcher.elasitcSearch.dao.Indexing;
//import com.jihun.booksearcher.elasitcSearch.model.IndexVo;
//import com.jihun.booksearcher.utils.BookCsvUploader;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItem;
//import org.apache.commons.io.IOUtils;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.search.SearchHit;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Log4j2
//@Service
//@RequiredArgsConstructor
//public class BookServiceImpl implements BookService {
//    private final Indexing<?> indexing;
//    private final EsClient esClient;
//
//
//    @Override
//    public Book search(String keyword) throws IOException {
//        Book result = new ArrayList<>();
//        Map<String, SearchResponse> searched = indexing.search(keyword);
//        Map<String, Object> titles = new HashMap<>();
//
//        for (String k : searched.keySet()) {
//            SearchHit[] hits = searched.get(k).getHits().getHits();
//            Arrays.stream(hits)
//                    .forEach(v -> {
////                        제목을 기준으로 중복된 검색 결과 제외
//                        String title = String.valueOf(v.getSourceAsMap().get("title"));
//                        String titleTrimmed = title.replace("\s", "");
//                        if (titles.get(titleTrimmed) == null) {
//                            result.add(new Book(v));
//                            titles.put(titleTrimmed, titleTrimmed);
//                        }
//                    });
//        }
//        return result;
//
//    }
//}