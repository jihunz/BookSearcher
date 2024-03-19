package com.jihun.booksearcher.book.controller;

import com.jihun.booksearcher.book.service.BookService;
import com.jihun.booksearcher.elasitcSearch.dao.Indexing;
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
	private final BookService<?> bookService;
	private final Indexing<?> indexing;

	/**
	 * 초기화 및 새로 인덱싱
	 * @param indexVo
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/bookUpload")
	public ResponseEntity<?> bookUpload(IndexVo indexVo) throws IOException{
		indexVo.setIndexName("book");
		String message =bookService.bookUpload(indexVo); 
		return ResponseEntity.ok(message);
	}


	/**
	 * 인덱스 삭제
	 * @param indexVo
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/deleteIndex")
	public ResponseEntity<?> initIndex(IndexVo indexVo) throws IOException{
		indexVo.setIndexName("book");
		String message =indexing.deleteIndex(indexVo.getIndexName());
		return ResponseEntity.ok(message);
	}

	@PostMapping("/uploadByFolder")//
	public ResponseEntity<?> uploadByFolder(String dirPath) throws IOException{
		String message = bookService.uploadByFolder(dirPath);
		return ResponseEntity.ok(message);
	}

	/**
	 * 검색
	 * @param keyword
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/search")
	public ResponseEntity<?> search(String keyword) throws IOException {
		return ResponseEntity.ok(bookService.search(keyword));
	}
	@GetMapping("/searchTest")
	public ResponseEntity<?> searchTest(String keyword) throws IOException {
		return ResponseEntity.ok(bookService.searchTest(keyword));
	}
}