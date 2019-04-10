package com.example.reorder.api;

import com.example.reorder.JoinResult;
import com.example.reorder.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApi {

    @FormUrlEncoded
    @POST("/signup")
    Call<JoinResult> postJoinUserInfo(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/login")
    Call<LoginResult>postLoginUserInfo(@FieldMap HashMap<String, String> map);

}
