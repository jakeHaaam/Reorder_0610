package com.example.reorder.UserInfoUpdate;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.reorder.Result.LoginResult;
import com.example.reorder.Api.RetrofitApi;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.CurrentUsingSeatInfo;
import com.example.reorder.globalVariables.IsLogin;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.StoreInfo;
import com.example.reorder.info.UserInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin {
    String url= serverURL.getUrl();

    public void Login(final String client_id,final String client_password, final Intent intent, final Activity activity){

        final String strclient_Id=client_id;
        final String strclient_password=client_password;

        try {
            HashMap<String, String> input = new HashMap<>();
            Log.d("11111", "UserLogin try 첫 줄");
            input.put("client_id", strclient_Id);
            input.put("client_password", strclient_password);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.postLoginUserInfo(input).enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if (response.isSuccessful()) {
                        LoginResult map = response.body();
                        Log.d("11111",map.getResult() +"");
                        if (map != null) {
                            switch (map.getResult()) {
                                case -1:
                                    Toast.makeText(activity,"잘못된 비밀번호 입니다.",Toast.LENGTH_SHORT).show();
                                    Log.d("11111", "잘못된 비밀번호입니다");
                                    break;
                                case 0:
                                    Toast.makeText(activity,"가입되지 않은 이메일입니다.",Toast.LENGTH_SHORT).show();
                                    Log.d("11111", "가입되지 않은 이메일입니다");
                                    break;
                                case 1:
                                    Log.d("11111", "login 성공");
                                    UserInfo userinfo = map.getUser();
                                    List<StoreInfo> storeInfo=map.getStore();
                                    CurrentUserInfo.getUser().setUserInfo(userinfo);
                                    CurrentStoreInfo.getStore().setStoreInfoList(storeInfo);
                                    CurrentUsingSeatInfo.setSeat_id(userinfo.getClient_seat_id());//고유번호가아닌거임
                                    IsLogin.setIsLogin(true);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                            }
                        }
                    }
                }


                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("login fail","");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.d("login catch","");
        }
    }
}
