package com.example.reorder.Api;

import com.example.reorder.Result.OrderAndSeatResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderAndSeatApi {
    @FormUrlEncoded
    @POST("/orderandseat")
    Call<OrderAndSeatResult> getResult(@FieldMap HashMap<String,String> map);
}