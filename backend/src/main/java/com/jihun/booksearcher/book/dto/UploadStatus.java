package com.jihun.booksearcher.book.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private Map<String, Boolean> uploadStat;
    private long numOfBooks = 0;
    private long uploadedBooks = 0;

    public void setFileInfo(File[] files) {
        this.numOfFile = files.length;
        this.uploadStat = Arrays.stream(files).collect(Collectors.toMap(k -> k.getName(), k -> false));
    }

    public void log() {
        log.info("UploadStatus: {}", this);
    }

    @Override
    public String toString() {
        return "UploadStatus{" +
                "numOfFile=" + numOfFile +
                ", uploadStat=" + uploadStat +
                ", numOfBooks=" + numOfBooks +
                ", uploadedBooks=" + uploadedBooks +
                '}';
    }
}
