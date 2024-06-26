package com.jihun.booksearcher.book.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private LocalDateTime startTime;
    private Duration elapseTime;


    public void initFileInfo(File[] files) {
        this.numOfFile = files.length;
        this.uploadResult = Arrays.stream(files).collect(Collectors.toMap(k -> k.getName(), v -> "0"));
    }
    public void initStartTime() {
        this.startTime = LocalDateTime.now();
    }

    public void initBooks() {
        this.numOfBooks = 0;
        this.uploadedBooks = 0;
    }

    public void initUploadStatus(File[] files) {
        this.initFileInfo(files);
        this.initStartTime();
        this.initBooks();

    }

    public void logResult() {
        this.setElapseTime(Duration.between(LocalDateTime.now(), this.startTime));
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
