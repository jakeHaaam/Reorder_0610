package com.example.reorder.globalVariables;

import com.example.reorder.info.User;

public class CurrentUserInfo {
    private static User user=new User();

    public static User getUser(){
        return user;
    }
    public static void setUser(User user){
        CurrentUserInfo.user=user;
    }
}
