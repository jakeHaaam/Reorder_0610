package com.example.reorder.info;

public class BookMarkStoreInfo {

    int id;
    int store_id;
    String store_name;
    String store_category;

    public BookMarkStoreInfo(int id, int store_id, String store_name, String store_category) {
        this.id = id;
        this.store_id = store_id;
        this.store_name = store_name;
        this.store_category = store_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_category() {
        return store_category;
    }

    public void setStore_category(String store_category) {
        this.store_category = store_category;
    }
}
