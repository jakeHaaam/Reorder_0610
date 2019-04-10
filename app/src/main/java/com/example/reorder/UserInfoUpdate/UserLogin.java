package com.example.reorder.UserInfoUpdate;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.reorder.LoginResult;
import com.example.reorder.api.RetrofitApi;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.IsLogin;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.UserInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin {
    String url= serverURL.getUrl();


    public void Login(final String client_email,final String client_password, final Intent intent, final Activity activity){

        final String strEmail=client_email;
        final String strPassword=client_password;

        try {
            HashMap<String, String> input = new HashMap<>();
            Log.d("11111", "UserLogin try 첫 줄");
            input.put("client_email", strEmail);
            input.put("client_password", strPassword);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.postLoginUserInfo(input).enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if (response.isSuccessful()) {
                        LoginResult map = response.body();
                        Log.d("11111", "손흥민 골");
                        Log.d("11111",map.getResult() +"");  // 1확인
                        //Log.d("11111",map.getUserInfo().getClient_email().toString());//
                        if (map != null) {
                            switch (map.getResult()) {
                                case -1:
                                    Log.d("11111", "잘못된 비밀번호입니다");
                                    break;
                                case 0:
                                    //Toast.makeText(LoginActivity.this,"가입되지 않은 이메일입니다.",Toast.LENGTH_SHORT).show();
                                    Log.d("11111", "가입되지 않은 이메일입니다");
                                    break;
                                case 1:
                                    Log.d("11111", "login 성공");
                                    UserInfo userinfo = map.getUserInfo();
                                   // Log.d("11111",userinfo.getClient_password()); // 이 코드도 작동 x
                                    Log.d("11111", "1??");
                                    CurrentUserInfo.getUser().setUserInfo(userinfo);
                                    UserInfo u_info = CurrentUserInfo.getUser().getUserInfo();
                                    Log.d("11111", "2??");

                                    Log.d("11111", "3??");
                                    //Log.d("11111",u_info.getClient_email());
                                    Log.d("11111", "로그인성공맞음1??");
//                                    userinfo.setChange(true);
                                    Log.d("11111", "로그인성공맞음2??");
                                    Log.d("11111",u_info +""); // null 값출력?? 왜지 .. 데이터 못받네
                                    //Log.d("11111",u_info.getClient_email());
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

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
