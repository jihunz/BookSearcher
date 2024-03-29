//package com.jihun.booksearcher.book.service;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import com.jihun.booksearcher.book.model.Book;
//import com.jihun.booksearcher.elasitcSearch.EsClient;
//import com.jihun.booksearcher.elasitcSearch.EsConfig;
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
//    @Override
//    public String upload(IndexVo indexVo) throws IOException {
//        List<Map<String, Object>> list = BookCsvUploader.ReadCsvFile(indexVo.getExcelFile());
//        return indexing.bulkIndexing(indexVo.getIndexName(), list);
//    }
//
//    @Override
//    public List<SearchHit[]> searchTest(String keyword) throws IOException {
//        List<SearchHit[]> result = new ArrayList<>();
//        Map<String, SearchResponse> searched = indexing.search(keyword);
//        for (String s : searched.keySet()) {
//            SearchHit[] hits = searched.get(s).getHits().getHits();
//            Arrays.stream(hits)
//                    .forEach(v -> {
//                        System.out.println(v.getScore());
//                    });
//            result.add(hits);
//        }
//        return result;
//    }
//
//    @Override
//    public String uploadByFolder(String dirPath) throws IOException {
//        File folder = new File(dirPath);
//        File[] files = folder.listFiles();
//
//        if (files != null) {
//            ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//            for (File file : files) {
//                if (file.isFile() && file.getName().endsWith(".csv")) {
//                    executorService.submit(() -> {
//                        try {
//                            log.info("[thread]: new thread created");
//                            List<Map<String, Object>> fileData = BookCsvUploader.ReadCsvFile(this.convert2MultipartFile(file));
//                            indexing.bulkIndexing("book", fileData);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } finally {
//                            log.info("[thread]: completed");
//                        }
//                    });
//                }
//            }
//
//            executorService.shutdown();
//            // 모든 작업이 완료될 때까지 대기
//        }
//        String msg = "[thread]: upload completed";
//        log.info(msg);
//        return msg;
//    }
//
//    private MultipartFile convert2MultipartFile(File file) throws IOException {
//        FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
//
//        InputStream is = new FileInputStream(file);
//        OutputStream os = fileItem.getOutputStream();
//        IOUtils.copy(is, os);
//
//        return new CommonsMultipartFile(fileItem);
//    }
//
//    @Override
//    public List<Book> search(String keyword) throws IOException {
//        List<Book> result = new ArrayList<>();
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