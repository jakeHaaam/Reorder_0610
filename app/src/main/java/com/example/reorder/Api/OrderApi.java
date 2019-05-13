package com.example.reorder.Api;

import com.example.reorder.Result.OrderResult;
import com.example.reorder.info.CartInfo;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderApi {
    @FormUrlEncoded
    @POST("/postorder")
    //Call<OrderResult> getResult(@FieldMap HashMap<String, List<Object>> map);
    //Call<OrderResult> getResult(@Field("order")  JSONObject jsonObject);
    //2개 이상 주문시
    Call<OrderResult> getResult(@Field("order") List<JSONObject> cartInfos);

    //1개 주문시
    @FormUrlEncoded
    @POST("/oneorder")
    Call<OrderResult> getResult(@FieldMap HashMap<String,String> map);
}
