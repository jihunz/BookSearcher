package com.jihun.booksearcher.book.service;


import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.jihun.booksearcher.book.util.UploadStatus;
import com.jihun.booksearcher.book.model.Book;
import com.jihun.booksearcher.elasticSearch.service.EsServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;


@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final EsServiceImpl esService;
    private long id = 1;
    private final UploadStatus uploadStatus;
    @Value("${elasticsearch.bulkSize}")
    private int BULK_SIZE;
    @Value("${elasticsearch.idxName}")
    private String IDX_NAME;
    Map<String, String> titles;


    private UploadStatus uploadByFolder(String dirPath, String idxName) throws IOException {
        File folder = new File(dirPath);
        File[] files = folder.listFiles();

        uploadStatus.initUploadStatus(files);

        if (files != null) {
            ExecutorService executorService = Executors.newFixedThreadPool(5);

            CountDownLatch latch = new CountDownLatch(files.length);

            for (File file : files) {
                String name = file.getName();
                if (file.isFile() && name.endsWith(".csv")) {
                    executorService.submit(() -> {
                        try {

                            log.info("[thread]: {} thread created", name);
                            List<Book> list = this.convert2List(file);
                            int eachUploadCnt = 0;

                            for (int i = 0; i < list.size(); i += BULK_SIZE) {
                                int endIdx = Math.min(i + BULK_SIZE, list.size());
                                List<Book> chunkedList = list.subList(i, endIdx);
                                BulkResponse res = esService.bulkIdx(chunkedList, idxName);

                                eachUploadCnt += res.items().size();
                                uploadStatus.setUploadedBooks(res.items().size());
                                String msg = uploadStatus.getUploadedRatio(list.size(), eachUploadCnt);
                                uploadStatus.getUploadResult().put(name, msg);
                                uploadStatus.logEach(name, msg);
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            log.info("[thread]: {} completed", name);
                            latch.countDown(); // 작업 완료 시 CountDownLatch 카운트 감소
                        }
                    });
                }
            }

            try {
                latch.await(); // 모든 스레드가 작업을 완료할 때까지 대기
            } catch (InterruptedException e) {
                log.error("Thread interrupted while waiting for tasks to complete.", e);
                Thread.currentThread().interrupt(); // 인터럽트 상태 재설정
            }

            executorService.shutdown();
        }

        uploadStatus.logResult();
        return uploadStatus;
    }

    private List<Book> convert2List(File file) {
        List<Book> list = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.skip(1);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (isEmptyRow(nextLine) || isEmptyColumn(nextLine, 10) || !isKorean(nextLine)) {
                    continue;
                }

                Book book = new Book();
                book.setId(id++);
                book.setIsbn(nextLine[1]); // ISBN 열
                book.setTitle(nextLine[3]); // 제목 열
                book.setAuthor(nextLine[4]); // 저자 열
                book.setPublisher(nextLine[5]); // 출판사 열
                book.setImg(nextLine[9]); // 이미지 URL 열
                book.setDescription(nextLine[10]); // 책 소개 열
                book.setKdc(!nextLine[11].isEmpty() ? nextLine[11] : null); // KDC 열
                list.add(book);
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        uploadStatus.setNumOfBooks(uploadStatus.getNumOfBooks() + list.size());
        return list;
    }

    @Override
    public UploadStatus execUpload(String dirPath) throws IOException {
        if (!esService.doesIndexExist(this.IDX_NAME)) esService.createIdxAndSettingMapping(this.IDX_NAME);
        return this.uploadByFolder(dirPath, this.IDX_NAME);
    }

    @Override
    public List<Book> searchTitleDesc(String keyword) throws IOException {
        this.titles = new HashMap<>();
        List<Book> result = new ArrayList<>();

        List<Hit<Book>> hit = esService.titleDescShouldQuery(keyword);
        hit.forEach(v -> {
            if (!isTitleDuplicated(v.source().getTitle())) {
                result.add(new Book(v));
            }
        });

        return result;
    }

    @Override
    public List<Book> searchSubInfo(String keyword) throws IOException {
        this.titles = new HashMap<>();
        List<Book> result = new ArrayList<>();

        List<Hit<Book>> hit = esService.subInfoShouldQuery(keyword);
        hit.forEach(v -> {
            if (!isTitleDuplicated(v.source().getTitle())) {
                result.add(new Book(v));
            }
        });

        return result;
    }

    private boolean isTitleDuplicated(String title) {
        String titleSource = title.replace("\s", "");
        if (this.titles.get(titleSource) == null) {
            this.titles.put(titleSource, titleSource);
            return false;
        }
        return true;
    }

    // 특정 행이 비어 있는지 확인하는 메서드
    private static boolean isEmptyRow(String[] row) {
        for (String cell : row) {
            if (!cell.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // 특정 열이 비어 있는지 확인하는 메서드
    private static boolean isEmptyColumn(String[] row, int columnIndex) {
        return row.length <= columnIndex || row[columnIndex].isEmpty();
    }

    private static boolean isKorean(String[] row) {
        String pattern = ".*[ㄱ-ㅎ가-힣]+.*"; // 한글을 포함하는 정규 표현식
        boolean title = Pattern.matches(pattern, row[3]);
        boolean desc = Pattern.matches(pattern, row[10]);

        return title && desc;
    }

//    private double calcMatchingPercentage(String str1, String str2) {
//        // 공백과 특수 문자 제거
//        String cleanStr1 = str1.replaceAll("[^a-zA-Z0-9]", "");
//        String cleanStr2 = str2.replaceAll("[^a-zA-Z0-9]", "");
//
//        // 두 문자열 중 길이가 더 짧은 문자열의 길이를 기준으로 일치하는 문자 수 계산
//        int minLength = Math.min(cleanStr1.length(), cleanStr2.length());
//        int matchingCount = 0;
//        for (int i = 0; i < minLength; i++) {
//            if (cleanStr1.charAt(i) == cleanStr2.charAt(i)) {
//                matchingCount++;
//            }
//        }
//
//        return (double) matchingCount / minLength * 100;
//    }

}
