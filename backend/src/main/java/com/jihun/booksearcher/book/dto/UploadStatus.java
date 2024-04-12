package com.jihun.booksearcher.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Data
@NoArgsConstructor
@Component 
public class UploadStatus {
    private int numOfFile = 0;
    private Map<String, String> uploadResult;
    private int numOfBooks = 0;
    private int uploadedBooks = 0;

    public void initFileInfo(File[] files) {
        this.numOfFile = files.length;
        this.uploadResult = Arrays.stream(files).collect(Collectors.toMap(k -> k.getName(), v -> "0"));
    }

    public void logResult() {
        log.info("[thread-upload completed]: {}", this);
    }
    public void logEach(String fileName, String msg) {
        log.info("[{}]: {}", fileName, msg);
    }

    @Override
    public String toString() {
        return "UploadStatus{" +
                "numOfFile=" + numOfFile +
                ", uploadStat=" + uploadResult +
                ", numOfBooks=" + numOfBooks +
                ", uploadedBooks=" + uploadedBooks +
                '}';
    }
    public String getUploadedRatio(int listSize, int resSize) {
        return String.format("%d / %d = %.2f%%", listSize, resSize, (float) resSize / listSize * 100);
    }

    public void setUploadedBooks(int num) {
        this.uploadedBooks += num;
    }
}
