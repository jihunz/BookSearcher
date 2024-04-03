package com.jihun.booksearcher.book.service;


import com.jihun.booksearcher.book.model.BookV2;
import com.jihun.booksearcher.book.util.csv.CustomMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceV2Impl implements BookServiceV2 {
    private final EsServiceImpl esService;


    @Override
    public String uploadByFolder(String dirPath) throws IOException {
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
        return null;
    }

    @Override
    public List<BookV2> upload(String csvFilePath) {
        try {
            String path = "/Users/jihunjang/Desktop/DJU/3학년/2인 프로젝트/도서 데이터/test/NL_BO_SPECIES_MASTER_NEW_202105.csv";
            log.info("[OpenCsv]: Reading CSV file: {}", path);


            //TODO: 설명이 비어있으면 해당 행 매핑 제외 코드 추가
            CustomMappingStrategy strategy;
            List<BookV2> list = new CsvToBeanBuilder(new FileReader(path))
                    .withType(BookV2.class)
                    .withMappingStrategy(strategy)
                    .withFilter(strings -> {
                        for (String one : strings) {
                            if (one != null && one.length() > 0) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .build()
                    .parse();

            for (BookV2 bookV2 : list) {
                System.out.println(bookV2.toString());
            }

            log.info("[OpenCsv]: Mapping objects from CSV file completed successfully.");
            return null;
        } catch (Exception e) {
            log.error("[OpenCsv]: Error occurred while reading CSV file and mapping objects: {}", e.getMessage());
            return null;
        }
    }

}
