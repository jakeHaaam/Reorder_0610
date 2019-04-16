package com.example.reorder.Result;

import com.example.reorder.info.StoreInfo;
import com.example.reorder.info.UserInfo;

import java.util.List;

public class LoginResult {
    int result; //서버가 보내주는 result
    UserInfo user;
    List<StoreInfo> store;

    public LoginResult(int result, UserInfo user, List<StoreInfo> store) {
        this.result = result;
        this.user = user;
        this.store = store;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<StoreInfo> getStore() {
        return store;
    }

    public void setStore(List<StoreInfo> store) {
        this.store = store;
    }
}
