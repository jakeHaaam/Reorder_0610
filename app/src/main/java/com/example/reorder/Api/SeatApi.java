package com.example.reorder.Api;

import com.example.reorder.Result.SeatResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SeatApi {
    @GET("/seatupdate/{id}")
    Call<SeatResult> setSeat(@Path("id") String id);

    @FormUrlEncoded
    @POST("/seatcancle")
    Call<SeatResult> setCancle(@FieldMap HashMap<String,String> map);

    @FormUrlEncoded
    @POST("/autoseatcancle")
    Call<SeatResult> setAutoCancle(@FieldMap HashMap<String,String> map);

    @FormUrlEncoded
    @POST("/userseatcancle")
    Call<SeatResult> setUserCancle(@FieldMap HashMap<String,String> map);
}