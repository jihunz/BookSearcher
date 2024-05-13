package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.enums.SearchType;
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
	public ResponseEntity<?> search(@RequestParam("type") String type, @RequestParam("keyword") String keyword) throws IOException {
		//TODO: case 값 enum으로 대체

		String subInfo = SearchType.SUB_INFO.getValue();

		switch (type) {
			case "desc":
				return ResponseEntity.ok(service.searchTitleDesc(keyword));
			case "subInfo":
				return ResponseEntity.ok(service.searchSubInfo(keyword));
			default:
				return null;
		}
	}
}