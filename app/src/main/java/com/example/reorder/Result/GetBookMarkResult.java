package com.example.reorder.Result;

import com.example.reorder.info.BookMarkStoreInfo;

import java.util.List;

public class GetBookMarkResult {
    int result;
    List<BookMarkStoreInfo> bookMarkStoreInfoList;

    public GetBookMarkResult(int result, List<BookMarkStoreInfo> bookMarkStoreInfoList) {
        this.result = result;
        this.bookMarkStoreInfoList = bookMarkStoreInfoList;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<BookMarkStoreInfo> getBookMarkStoreInfoList() {
        return bookMarkStoreInfoList;
    }

    public void setBookMarkStoreInfoList(List<BookMarkStoreInfo> bookMarkStoreInfoList) {
        this.bookMarkStoreInfoList = bookMarkStoreInfoList;
    }
}