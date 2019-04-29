package com.example.reorder.globalVariables;

import com.example.reorder.info.BookMarkStore;

public class CurrentBookMarkStoreInfo {

    private static BookMarkStore bookMarkStore=new BookMarkStore();

    public static BookMarkStore getBookMarkStore() {        return bookMarkStore;    }

    public static void setBookMarkStore(BookMarkStore bookMarkStore) {        CurrentBookMarkStoreInfo.bookMarkStore = bookMarkStore; }
}
