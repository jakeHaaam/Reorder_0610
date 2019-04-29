package com.example.reorder.info;

public class StoreMenuInfo {
    int id;
    int menu_id;
    String menu_name;
    String menu_info;
    int menu_price;

    public StoreMenuInfo(int id, int menu_id, String menu_name, String menu_info, int menu_price) {
        this.id = id;
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.menu_info = menu_info;
        this.menu_price = menu_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getMenu_info() {
        return menu_info;
    }

    public void setMenu_info(String menu_info) {
        this.menu_info = menu_info;
    }

    public int getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(int menu_price) {
        this.menu_price = menu_price;
    }
}
