//package com.jihun.booksearcher.utils;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Log4j2
//@RequiredArgsConstructor
//public class BookCsvUploader {
//    public static List<Map<String, Object>> ReadCsvFile(MultipartFile file) throws IOException {
//
//        List<Map<String, Object>> data = new ArrayList<>();
//
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("Uploaded file is empty");
//        }
//
//        try (InputStream inputStream = file.getInputStream();
//             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//             CSVParser csvParser = new CSVParser(inputStreamReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())
//        ) {
//            log.info("pasing Start");
//            data = csvParser.getRecords().stream()
//                    .filter(record -> {
//                        String isbnStr = record.get("ISBN_THIRTEEN_NO");
//                        return isbnStr != null && !isbnStr.toLowerCase().contains("x") && !(isbnStr.length() > 13);
//                    })
//                    .map(record -> {
//                        Map<String, Object> itemMap = new HashMap<>();
//
//                        String desc = record.get("BOOK_INTRCN_CN");
//                        if (StringUtils.isNotBlank(desc)) {
//                            String isbnThirteenNo = record.get("ISBN_THIRTEEN_NO");
//                            String isbn = String.valueOf(isbnThirteenNo);
//                            String title = record.get("TITLE_NM");
//                            String author = record.get("AUTHR_NM");
//                            String publisher = record.get("PUBLISHER_NM");
//                            String image = record.get("IMAGE_URL");
//                            String description = desc;
//                            String kdcStr = record.get("KDC_NM");
//                            if (kdcStr.length() > 13) kdcStr = kdcStr.substring(0, 13);
//
//                            itemMap.put("isbn", isbn);
//                            itemMap.put("title", title);
//                            itemMap.put("author", author);
//                            itemMap.put("publisher", publisher);
//                            itemMap.put("image", image);
//                            itemMap.put("description", description);
//                            if (!StringUtils.isBlank(kdcStr)) itemMap.put("kdc", kdcStr.replaceAll("\\D", ""));
//                        }
//                        return itemMap;
//
//                    })
//                    .collect(Collectors.toList());
//        } catch (IOException e) {
//            log.error("fileParsing error ", e);
//        }
//        log.info("parsing success DataCount = " + data.size());
//        return data;
//    }
//}