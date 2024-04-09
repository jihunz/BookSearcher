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
    private long numOfBooks = 0;
    private long uploadedBooks = 0;

    public void initFileInfo(File[] files) {
        this.numOfFile = files.length;
        this.uploadResult = Arrays.stream(files).collect(Collectors.toMap(k -> k.getName(), v -> "0"));
    }

    public void logResult() {
        log.info("[thread-upload completed]: {}", this);
    }
    public void logEach(String msg) {
        log.info("[rows / uploaded rows]: " + msg);
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
        return String.format("%d / %d = %.2f%%", listSize, resSize, (float) listSize / resSize * 100);
    }
}
