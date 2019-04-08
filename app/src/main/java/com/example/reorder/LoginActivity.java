package com.example.reorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.api.LoginApi;
import com.example.reorder.info.UserInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText et_login_id;
    EditText et_login_password;
    Button bt_login_ok;
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton=(TextView)findViewById(R.id.tv_login_register);
        et_login_id=(EditText)findViewById(R.id.et_login_id);
        et_login_password=(EditText)findViewById(R.id.et_login_password);
        bt_login_ok=(Button)findViewById(R.id.bt_login_ok);


        //bundle 무엇?
        Bundle bundle = getIntent().getExtras();     //**회원가입 페이지에서 넘어온 값(데이터 자동저장 아닐 시 주석처리)
        if(bundle != null) {                            //UserInfo 클래스에서 따로 "_id"를 지정하지않음
            UserInfo userInfo = bundle.getParcelable("userinfo");
            et_login_id.setText(userInfo.getClient_id()); //***clinet_id맞나?
            et_login_password.setText(userInfo.getClient_password());//***clinet_password맞나?
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bt_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> input=new HashMap<>();
                input.put("client_email",et_login_id.getText().toString());
                input.put("client_password",et_login_password.getText().toString());

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://35.197.38.155/")
                        .addConverterFactory(GsonConverterFactory.create()).build();
                LoginApi loginApi = retrofit.create(LoginApi.class);
                loginApi.postUserInfo(input).enqueue(new Callback<LoginResult>(){

                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response){

                        if(response.isSuccessful()){
                            LoginResult map=response.body();
                            if(map!=null){
                                switch(map.getResult()){
                                    case -1:
                                        Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0:
                                        Toast.makeText(LoginActivity.this, "가입되지 않은 유저 입니다.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(LoginActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
//                                        UserInfo userinfo = new UserInfo(map.getUserInfo().getClient_id(),map.getUserInfo().getClient_password());
                                        Intent intent = new Intent(LoginActivity.this,NavigationActivity.class);//메인화면 대신 임시 지정
//                                        intent.putExtra("UserInfo",userinfo);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {

                    }
                });
                //startActivity(new Intent(LoginActivity.this,NavigationActivity.class));//메인화면 대신 임시 지정
            }

        });



         /* 예전코드

         if(!et_login_id.getText().toString().equals("xodn0996")
                        || !et_login_password.getText().toString().equals("qwqw1210"))
                    //DB에 있는 ID값들과 비교해서 존재하면 넘어가기+DB에 해당 ID의 비밀번호와 비교해서 일치하면 넘어가기
                {
                    Toast.makeText(getApplicationContext(),"ID 또는 패스워드가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                }
                else if(et_login_id.getText().toString().equals("xodn0996")
                        && et_login_password.getText().toString().equals("qwqw1210"))
                    //DB에 있는 ID,비밀번호 값이 일치하면
                {
                    Toast.makeText(getApplicationContext(),"로그인 되었습니다.",Toast.LENGTH_SHORT).show();
                    Intent NaviIntent=new Intent(LoginActivity.this,NavigationActivity.class);
                    LoginActivity.this.startActivity(NaviIntent);
                }
            }*/
    }
}