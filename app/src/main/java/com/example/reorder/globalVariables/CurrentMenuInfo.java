package com.example.reorder.globalVariables;

public class CurrentMenuInfo {

    public static int storeinfo_id;
    public static int menu_id;
    public static String menu_name ;
    public static int menu_price;
    public static int menu_count;

    public static int getMenu_id() {
        return menu_id;
    }

    public static void setMenu_id(int menu_id) {
        CurrentMenuInfo.menu_id = menu_id;
    }

    public static String getMenu_name() {
        return menu_name;
    }

    public static void setMenu_name(String menu_name) {
        CurrentMenuInfo.menu_name = menu_name;
    }

    public static int getMenu_price() {
        return menu_price;
    }

    public static void setMenu_price(int menu_price) {
        CurrentMenuInfo.menu_price = menu_price;
    }

    public static int getMenu_count() {
        return menu_count;
    }

    public static void setMenu_count(int menu_count) {
        CurrentMenuInfo.menu_count = menu_count;
    }

    public static int getStoreinfo_id() {
        return storeinfo_id;
    }

    public static void setStoreinfo_id(int storeinfo_id) {
        CurrentMenuInfo.storeinfo_id = storeinfo_id;
    }
}