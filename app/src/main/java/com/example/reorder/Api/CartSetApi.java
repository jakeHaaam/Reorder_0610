package com.example.reorder.Api;

import com.example.reorder.Result.CartResult;
import com.example.reorder.Result.JoinResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartSetApi {

    @FormUrlEncoded
    @POST("/setcart")
    Call<JoinResult> setUserCartInfo(@FieldMap HashMap<String,String> map);

    @GET("/setcart/{id}")
    Call<CartResult> getId(@Path("id") int id);
}
