package com.example.reorder.api;

import com.example.reorder.Result.StoreIdResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreIdApi {
    @GET("/check/{store_id}")
    Call<StoreIdResult> getStore_id(@Path("store_id") String store_id);
}
