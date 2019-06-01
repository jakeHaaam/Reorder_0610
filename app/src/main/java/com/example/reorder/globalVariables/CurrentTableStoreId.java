package com.example.reorder.globalVariables;

public class CurrentTableStoreId {
    private static String store_id;

    public CurrentTableStoreId() {
    }

    public static String getStore_id() {
        return store_id;
    }

    public static void setStore_id(String store_id) {
        CurrentTableStoreId.store_id = store_id;
    }
}
