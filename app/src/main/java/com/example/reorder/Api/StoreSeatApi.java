package com.example.reorder.Api;

import com.example.reorder.Result.StoreSeatResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StoreSeatApi {
    //    @FormUrlEncoded
//    @POST("/storeseat")
//    Call<StoreSeatResult> getResult(@FieldMap HashMap<String,String> map);
    @GET("/storeseat/{store_id}")
    Call<StoreSeatResult> getResult(@Path("store_id") String store_id);
}
