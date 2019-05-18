package com.example.reorder.Api;

import com.example.reorder.Result.LiveOrderStateResult;
import com.example.reorder.Result.SeeTableResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LiveOrderStateApi {
    @GET("/liveorderstate/{id}")//이 id는 client의 고유 id
    Call<LiveOrderStateResult> getLiveOrderState(@Path("id") String id);
}