package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.service.BookServiceV2;
import com.jihun.booksearcher.elasitcSearch.model.IndexVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

	private final BookServiceV2 bookServiceV2;


	@PostMapping("/upload")
	public ResponseEntity<?> upload() throws IOException {
		bookServiceV2.upload();

		return ResponseEntity.ok(null);
	}

//	private final BookService<?> bookService;
//	private final Indexing<?> indexing;
//
//	@PostMapping("/upload")
//	public ResponseEntity<?> upload(IndexVo indexVo) throws IOException{
//		indexVo.setIndexName("book");
//		String message =bookService.upload(indexVo);
//		return ResponseEntity.ok(message);
//	}
//
//
//	@PostMapping("/deleteIndex")
//	public ResponseEntity<?> deleteIndex(IndexVo indexVo) throws IOException{
//		indexVo.setIndexName("book");
//		String message =indexing.deleteIndex(indexVo.getIndexName());
//		return ResponseEntity.ok(message);
//	}
//
//	@PostMapping("/uploadByFolder")//
//	public ResponseEntity<?> uploadByFolder(String dirPath) throws IOException{
//		String message = bookService.uploadByFolder(dirPath);
//		return ResponseEntity.ok(message);
//	}
//
//	@GetMapping("/search")
//	public ResponseEntity<?> search(String keyword) throws IOException {
//		return ResponseEntity.ok(bookService.search(keyword));
//	}
//	@GetMapping("/searchTest")
//	public ResponseEntity<?> searchTest(String keyword) throws IOException {
//		return ResponseEntity.ok(bookService.searchTest(keyword));
//	}
}