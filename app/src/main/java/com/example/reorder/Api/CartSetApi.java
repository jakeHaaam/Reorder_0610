package com.example.reorder.Api;

import com.example.reorder.Result.JoinResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CartSetApi {
    @FormUrlEncoded
    @POST("/setcart")
    Call<JoinResult> setUserCartInfo(@FieldMap HashMap<String,String> map);
}