package com.example.reorder.Api;

import com.example.reorder.Result.SeatResult;
import com.example.reorder.Result.SeeTableResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeeTableApi {
    @GET("/seetable/{store_id}")
    Call<SeeTableResult> gettable(@Path("store_id") String store_id);
}
