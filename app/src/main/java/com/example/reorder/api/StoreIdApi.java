package com.example.reorder.api;

import com.example.reorder.Result.IdCheckResult;
import com.example.reorder.Result.StoreIdResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoreIdApi {
    @GET("/storecheck/{storeinfo_id}")
    Call<StoreIdResult> getStore_id(@Path("storeinfo_id") String storeinfo_id);
}

