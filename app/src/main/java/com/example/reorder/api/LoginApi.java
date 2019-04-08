package com.example.reorder.api;

import com.example.reorder.LoginResult;
import com.example.reorder.JoinResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResult> postUserInfo(@FieldMap HashMap<String,String> map);
}
