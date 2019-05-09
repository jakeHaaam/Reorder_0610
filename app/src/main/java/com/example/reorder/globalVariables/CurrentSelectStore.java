package com.example.reorder.globalVariables;

public class CurrentSelectStore {
    public static int id;
    public static int st_id;
    public static String st_name;
    public static String st_category;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        CurrentSelectStore.id = id;
    }

    public static int getSt_id() {
        return st_id;
    }

    public static void setSt_id(int st_id) {
        CurrentSelectStore.st_id = st_id;
    }

    public static String getSt_name() {
        return st_name;
    }

    public static void setSt_name(String st_name) {
        CurrentSelectStore.st_name = st_name;
    }

    public static String getSt_category() {
        return st_category;
    }

    public static void setSt_category(String st_category) {
        CurrentSelectStore.st_category = st_category;
    }
}