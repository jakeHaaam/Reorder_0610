package com.example.reorder.Api;

import com.example.reorder.Result.IdCheckResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IdCheckApi {
    @GET("/check/{client_id}")
    Call<IdCheckResult> getClient_id(@Path("client_id") String client_id);
}
