package com.example.reorder;

import com.example.reorder.info.UserInfo;

public class LoginResult {
    int result; //서버가 보내주는 result
    UserInfo user;

    public LoginResult(int result,UserInfo user){
        this.result=result;
        this.user=user;
    }

    public int getResult(){
        return result;
    }
    public UserInfo getUserInfo(){
        return user;
    }

}
