package com.jihun.booksearcher.book.service;


import co.elastic.clients.elasticsearch.core.BulkResponse;
import com.jihun.booksearcher.book.dto.UploadStatus;
import com.jihun.booksearcher.book.model.BookV2;
import com.jihun.booksearcher.elasitcSearch.service.EsServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class BookServiceV2Impl implements BookServiceV2 {
    private final EsServiceImpl esService;
    private long id = 1;
    private final UploadStatus uploadStatus;


    @Override
    public UploadStatus uploadByFolder(String dirPath) throws IOException {
        File folder = new File(dirPath);
        File[] files = folder.listFiles();

        uploadStatus.setFileInfo(files);

        if (files != null) {
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            CountDownLatch latch = new CountDownLatch(files.length);

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    executorService.submit(() -> {
                        try {
                            log.info("[thread]: new thread created");
                            BulkResponse res = esService.index(this.convert2List(file));
                            uploadStatus.setUploadedBooks(uploadStatus.getUploadedBooks() + Long.valueOf(res.items().size()));

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            uploadStatus.getUploadStat().replace(file.getName(), true);
                            log.info("[thread]: completed");
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

        log.info("[thread]: upload completed");
        uploadStatus.log();
        return uploadStatus;
    }

    @Override
    public List<BookV2> convert2List(File file) {
        List<BookV2> list = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.skip(1);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (isEmptyRow(nextLine) || isEmptyColumn(nextLine, 10) || !isKorean(nextLine)) {
                    continue;
                }

                BookV2 book = new BookV2();
                book.setId(id++);
                book.setIsbn(nextLine[1]); // ISBN 열
                book.setTitle(nextLine[3]); // 제목 열
                book.setAuthor(nextLine[4]); // 저자 열
                book.setPublisher(nextLine[5]); // 출판사 열
                book.setImg(nextLine[9]); // 이미지 URL 열
                book.setDescription(nextLine[10]); // 책 소개 열
                book.setKdc(!nextLine[11].isEmpty() ? nextLine[11] : null); // KDC 열
                list.add(book);

                long numOfBooks = uploadStatus.getNumOfBooks();
                uploadStatus.setNumOfBooks(numOfBooks + 1);
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return list;
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
}
