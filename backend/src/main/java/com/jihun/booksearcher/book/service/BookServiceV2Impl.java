package com.jihun.booksearcher.book.service;


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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceV2Impl implements BookServiceV2 {
    private final EsServiceImpl esService;
    private long id = 1;
    private long cnt = 0;
    private Map<String, Integer> fileMap;


    @Override
    public String uploadByFolder(String dirPath) throws IOException {
        File folder = new File(dirPath);
        File[] files = folder.listFiles();

        fileMap = Arrays.stream(files).collect(Collectors.toMap(k -> k.getName(), k -> 0));

        if (files != null) {
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            CountDownLatch latch = new CountDownLatch(files.length);

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    executorService.submit(() -> {
                        try {
                            log.info("[thread]: new thread created");
                            List<BookV2> list = this.upload(file);
                            esService.index(list);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            fileMap.replace(file.getName(), 1);
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

        log.info("[file upload status]: ");
        for (Map.Entry<String, Integer> item : fileMap.entrySet()) {
            log.info(item.getKey() + ":" + item.getValue());
        }

        String msg = "[thread]: upload completed";
        log.info(msg);
        log.info("[number of books]: " + String.valueOf(cnt));

        return msg;
    }

    private Map<String, String> logStat() {

        //1. 파일 개수, 2. 파일별 업로드 결과, 3. 한글 도서 개수, 4. 업로드한 한글 도서 개수
        Map<String, String> result = new HashMap<>()

                ;
        return result;
    }


    @Override
    public List<BookV2> upload(File file) {
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

                this.cnt++;
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
