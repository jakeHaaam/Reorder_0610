package com.example.reorder.globalVariables;

import android.app.Application;

public class serverURL extends Application {
    private static String url="http://34.85.56.49:4000";
    public static String getUrl() {
        return url;
    }
}
