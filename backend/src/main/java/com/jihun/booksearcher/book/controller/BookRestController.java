package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.enums.SearchType;
import com.jihun.booksearcher.book.service.BookService;
import com.jihun.booksearcher.elasticSearch.service.EsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static com.jihun.booksearcher.book.enums.SearchType.DESC;
import static com.jihun.booksearcher.book.enums.SearchType.SUB_INFO;


@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

	private final BookService service;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestBody Map<String, String> body) throws IOException {
		return ResponseEntity.ok(service.execUpload(body.get("dirPath")));
	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("type") String type, @RequestParam("keyword") String keyword) throws IOException {
		SearchType searchType = SearchType.findByVal(type);

        return switch (searchType) {
			case DESC -> ResponseEntity.ok(service.searchTitleDesc(keyword));
			case SUB_INFO -> ResponseEntity.ok(service.searchSubInfo(keyword));
		};
	}
}