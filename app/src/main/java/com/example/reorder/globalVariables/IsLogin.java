package com.example.reorder.globalVariables;

import android.app.Application;

public class IsLogin extends Application {
    private static boolean isLogin=false;

    public static boolean isLogin() {return isLogin;}

    public static void setIsLogin(boolean isLogin){
        IsLogin.isLogin=isLogin;}
}
