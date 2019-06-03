package com.example.reorder.Api;

import com.example.reorder.Result.IdCheckResult;
import com.example.reorder.Result.MileageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetMileageApi {
    @GET("/getmileage/{client_id}")
    Call<MileageResult> getMileage(@Path("client_id") String client_id);
}
