package com.example.reorder.info;

public class PastOrderInfo {//주문한 날짜(+시간),주문완성 시각, 주문한 메뉴,갯수,가격,사용한 마일리지
    String order_date;
    String complate_time;
    String menu_name;
    String menu_count;
    String menu_price;
    String used_mileage;
    String order_serial;
    String store_name;

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getComplate_time() {
        return complate_time;
    }

    public void setComplate_time(String complate_time) {
        this.complate_time = complate_time;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_count() {
        return menu_count;
    }

    public void setMenu_count(String menu_count) {
        this.menu_count = menu_count;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getUsed_mileage() {
        return used_mileage;
    }

    public void setUsed_mileage(String used_mileage) {
        this.used_mileage = used_mileage;
    }

    public String getOrder_serial() {
        return order_serial;
    }

    public void setOrder_serial(String order_serial) {
        this.order_serial = order_serial;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public PastOrderInfo(String order_date, String complate_time, String menu_name, String menu_count, String menu_price, String used_mileage, String order_serial, String store_name) {
        this.order_date = order_date;
        this.complate_time = complate_time;
        this.menu_name = menu_name;
        this.menu_count = menu_count;
        this.menu_price = menu_price;
        this.used_mileage = used_mileage;
        this.order_serial = order_serial;
        this.store_name = store_name;
    }
}