package com.example.reorder.Api;

import com.example.reorder.Result.SeatResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeatApi {
    @GET("/seatupdate/{id}")
    Call<SeatResult> setSeat(@Path("id") String id);

    @GET("/seatcancle/{id}")
    Call<SeatResult> setCancle(@Path("id") String id);

    @GET("/autoseatcancle/{id}")
    Call<SeatResult> setAutoCancle(@Path("id") String id);

    @GET("/userseatcancle/{id}")
    Call<SeatResult> setUserCancle(@Path("id") String id);
}