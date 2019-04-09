package com.example.reorder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.UserInfoUpdate.UserLogin;
import com.example.reorder.api.LoginApi;
import com.example.reorder.info.UserInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText et_login_id;//[client_email받는 버튼]
    EditText et_login_password;
    Button bt_login_ok;
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }
        registerButton=(TextView)findViewById(R.id.tv_login_register);
        et_login_id=(EditText)findViewById(R.id.et_login_id);
        et_login_password=(EditText)findViewById(R.id.et_login_password);
        bt_login_ok=(Button)findViewById(R.id.bt_login_ok);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        bt_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_login_id.getText().toString().equals("")||et_login_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    Log.d("11111", "입력해");
                }
                else{
                    UserLogin userLogin = new UserLogin();
                    userLogin.Login(et_login_id.getText().toString(),et_login_password.getText().toString(),new Intent(LoginActivity.this, NavigationActivity.class),LoginActivity.this);
                    Log.d("11111", "여기는왔니");
                }

            }
        });

    }

}




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
