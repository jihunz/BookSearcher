package com.jihun.booksearcher.elasitcSearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
public class IndexVo {
	private String indexName;
	private String fileName;
	private String keyword;
	private Integer indexSeq;
	private MultipartFile excelFile;
	
}