package com.example.reorder.Api;
import com.example.reorder.Result.BookMarkResult;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface BookMarkApi {

    @FormUrlEncoded
    @POST("/bookmark")
    Call<BookMarkResult> postBookmarkInfo(@FieldMap HashMap<String,String> map);
}