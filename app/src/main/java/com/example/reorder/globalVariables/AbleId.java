package com.example.reorder.globalVariables;

public class AbleId {
    private static String id;

    public AbleId() {
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        AbleId.id = id;
    }
}