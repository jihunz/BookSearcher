package com.jihun.booksearcher.elasitcSearch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndexEnum {

    BOOK("도서", "book", new String[]{"isbn", "title", "author", "publisher", "image", "description", "kdc","titleSub","authorSub"}, new String[]{"isbn","title", "author", "publisher", "content"});

    private final String description;
    private final String name;
    private final String[] fields;
    private final String[] searchRange;

    public static boolean isBook(String indexName) {
        return BOOK.name.equals(indexName);
    }

    public static String findPath(String indexName, String type) {
        for(IndexEnum index : IndexEnum.values()) {
            if(index.getName().equals(indexName)) {
            	return "/srchSetting/index_"+type+"_" + indexName + ".json";
            }
        }
        throw new IllegalArgumentException("Index File Path Not Found... Wrong indexName : " + indexName);
    }

}