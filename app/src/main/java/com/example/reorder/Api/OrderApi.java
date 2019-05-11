package com.example.reorder.Api;

import com.example.reorder.Result.OrderResult;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderApi {
    @FormUrlEncoded
    @POST("/postorder")
    //Call<OrderResult> getResult(@FieldMap HashMap<String, JSONObject> map);
    Call<OrderResult> getResult(@Body JSONObject jsonObject);
}
