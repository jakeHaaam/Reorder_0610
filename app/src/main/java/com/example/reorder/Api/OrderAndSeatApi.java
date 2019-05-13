package com.example.reorder.Api;

import com.example.reorder.Result.OrderAndSeatResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderAndSeatApi {
    @FormUrlEncoded
    @POST("/orderandseat")//2개 이상 주문시
    Call<OrderAndSeatResult> getResult(@Field("orderandseat") List<JSONObject> jsonObjects);

    //1개 주문시
    @FormUrlEncoded
    @POST("/oneorderandseat")
    Call<OrderAndSeatResult> getResult(@FieldMap HashMap<String,String> map);
}