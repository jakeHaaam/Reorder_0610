package com.example.reorder.Api;

import com.example.reorder.Result.GetBookMarkResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetBookMarkApi {
    @GET("/get/{bookmarkstore}")
    Call<GetBookMarkResult> getid(@Path("id") String id);
}