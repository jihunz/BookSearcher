package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.model.SynonymReq;
import com.jihun.booksearcher.elasticSearch.service.SynonymServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/synonym")
@RequiredArgsConstructor
public class SynonymRestController {

    private final SynonymServiceImpl synonymService;

//    @GetMapping("/{id}")
//    public ResponseEntity<?> item(@PathVariable String id) {
//        try {
//            return ResponseEntity.ok(synonymService.item(id));
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody List<String> request) {
        try {
            return ResponseEntity.ok(synonymService.update(id, request));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            return ResponseEntity.ok(synonymService.delete(id));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
