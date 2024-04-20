package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.service.BookServiceV2;
import com.jihun.booksearcher.elasticSearch.service.EsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

	private final BookServiceV2 service;
	private final EsServiceImpl esService;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestBody Map<String, String> body) throws IOException {
		return ResponseEntity.ok(esService.addSettingMapping());
//		return ResponseEntity.ok(service.uploadByFolder(body.get("dirPath")));
	}

//	@GetMapping("/search")
//	public ResponseEntity<?> search(String keyword) throws IOException {
//		return ResponseEntity.ok(bookService.search(keyword));
//	}
//	@GetMapping("/searchTest")
//	public ResponseEntity<?> searchTest(String keyword) throws IOException {
//		return ResponseEntity.ok(bookService.searchTest(keyword));
//	}
}