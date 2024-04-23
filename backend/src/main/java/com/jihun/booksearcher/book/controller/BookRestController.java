package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.service.BookService;
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

	private final BookService service;
	private final EsServiceImpl esService;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestBody Map<String, String> body) throws IOException {
		return ResponseEntity.ok(service.execUpload(body.get("dirPath")));
	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("keyword") String keyword) throws IOException {
//		return ResponseEntity.ok(service.search(keyword));
		return ResponseEntity.ok(esService.descMustQuery(keyword));
	}
}