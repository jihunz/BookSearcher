package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.service.BookServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

	private final BookServiceV2 bookServiceV2;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestBody Map<String, String> body) throws IOException {
		bookServiceV2.uploadByFolder(body.get("dirPath"));

		return ResponseEntity.ok(null);
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