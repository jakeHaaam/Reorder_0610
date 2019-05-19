package com.example.reorder.Api;

import com.example.reorder.Result.LiveOrderStateResult;
import com.example.reorder.Result.PastOrderResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PastOrderApi {
    @GET("/historyorder/{id}")//이 id는 client의 고유 id
    Call<PastOrderResult> getPastOrder(@Path("id") String id);
}