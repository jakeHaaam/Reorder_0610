package com.example.reorder.Api;

import com.example.reorder.Result.OrderResult;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderApi {
    @FormUrlEncoded
    @POST("/postorder")
    Call<OrderResult> getReslut(@FieldMap HashMap<String, ArrayList> map);
}
