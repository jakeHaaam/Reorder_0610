package com.example.reorder.info;

public class CartInfo {
    int menu_id;
    String menu_name;
    int menu_price;
    int menu_count;
    int store_id;

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(int menu_price) {
        this.menu_price = menu_price;
    }

    public int getMenu_count() {
        return menu_count;
    }

    public void setMenu_count(int menu_count) {
        this.menu_count = menu_count;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public CartInfo(int menu_id, String menu_name, int menu_price, int menu_count, int store_id) {
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.menu_price = menu_price;
        this.menu_count = menu_count;
        this.store_id = store_id;
    }
}