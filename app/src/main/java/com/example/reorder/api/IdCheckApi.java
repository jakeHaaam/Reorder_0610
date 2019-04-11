package com.example.reorder.api;

import com.example.reorder.Result.IdCheckResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IdCheckApi {
    @GET("/check/{client_email}")
    Call<IdCheckResult> getClient_email(@Path("client_email") String client_email);
}
