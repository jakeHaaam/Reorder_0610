package com.example.reorder.Api;

import com.example.reorder.Result.DeleteCartResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DeleteCartApi {
    @GET("/deletecart/{id}")
    Call<DeleteCartResult> getid(@Path("id") String id);
}
