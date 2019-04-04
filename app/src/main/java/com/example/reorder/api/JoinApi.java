package com.example.reorder.api;

import com.example.reorder.JoinResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JoinApi {
    @FormUrlEncoded
    @POST("/users")
    Call<JoinResult> postJoinUserInfo(@FieldMap HashMap<String,String>map);
}
