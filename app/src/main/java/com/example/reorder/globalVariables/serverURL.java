package com.example.reorder.globalVariables;

import android.app.Application;

public class serverURL extends Application {
    private static String url="http://35.197.38.155:4000";
    public static String getUrl() {
        return url;
    }
}
