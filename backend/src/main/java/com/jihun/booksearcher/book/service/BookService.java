//package com.jihun.booksearcher.book.service;
//
//import com.jihun.booksearcher.book.model.Book;
//import com.jihun.booksearcher.elasitcSearch.model.IndexVo;
//import org.elasticsearch.search.SearchHit;
//
//import java.io.IOException;
//import java.util.List;
//
//public interface BookService<T> {
//
//	List<Book> search(String indexVo) throws IOException;
//
//	String upload(IndexVo indexVo) throws IOException;
//
//
//	List<SearchHit[]> searchTest(String keyword) throws IOException;
//
//	String uploadByFolder(String dirPath) throws IOException;
//
//}