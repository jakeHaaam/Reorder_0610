package com.example.reorder.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reorder.Result.IdCheckResult;
import com.example.reorder.Result.JoinResult;
import com.example.reorder.R;
import com.example.reorder.Api.IdCheckApi;
import com.example.reorder.Api.JoinApi;
import com.example.reorder.globalVariables.AbleId;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    Button bt_sign_up_ok;
    Button bt_sign_up_id_check;
    EditText et_sign_up_id;
    EditText et_sign_up_password;
    EditText et_sign_up_password_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bt_sign_up_ok=(Button)findViewById(R.id.bt_sign_up_ok);
        et_sign_up_id=(EditText)findViewById(R.id.et_sign_up_id);
        et_sign_up_password=(EditText)findViewById(R.id.et_sign_up_password);
        et_sign_up_password_check=(EditText)findViewById(R.id.et_sign_up_password_check);
        bt_sign_up_id_check=(Button)findViewById(R.id.bt_sign_up_id_check);

        //아이디 중복 체크
        bt_sign_up_id_check.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String client_id= et_sign_up_id.getText().toString();

                if(et_sign_up_id.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"ID를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    bt_sign_up_ok.setClickable(false);
                }

                else
                    try {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://34.85.56.49:4000")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        IdCheckApi idCheckAPI = retrofit.create(IdCheckApi.class);
                        idCheckAPI.getClient_id(client_id).enqueue(new Callback<IdCheckResult>() {
                            @Override
                            public void onResponse(Call<IdCheckResult> call, Response<IdCheckResult> response) {
                                if (response.isSuccessful()) {
                                    IdCheckResult idCheckResult = response.body();
                                    switch (idCheckResult.getResult()) {
                                        case 0:
                                            Toast.makeText(getApplicationContext(), "이미 가입된 ID입니다.", Toast.LENGTH_SHORT).show();
                                            bt_sign_up_ok.setClickable(false);
                                            break;
                                        case 1:
                                            Toast.makeText(getApplicationContext(), "사용 가능한 ID입니다.", Toast.LENGTH_SHORT).show();
                                            bt_sign_up_ok.setClickable(true);
                                            AbleId.setId(client_id);
                                            break;
                                        default:
                                            Toast.makeText(getApplicationContext(), "login is NOT Successful", Toast.LENGTH_SHORT).show();
                                    }
                                } else
                                    Toast.makeText(getApplicationContext(), "response is NOT Successful", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<IdCheckResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "This is fail", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });



        //비밀번호 체크
        bt_sign_up_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_sign_up_password.getText().toString().equals("")||et_sign_up_password_check.getText().toString().equals("")
                        || et_sign_up_id.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"모든 항목을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                if(!et_sign_up_password.getText().toString().equals(et_sign_up_password_check.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        HashMap<String, String> input = new HashMap<>();
                        input.put("client_id", et_sign_up_id.getText().toString());
                        input.put("client_password", et_sign_up_password.getText().toString());

                        if(et_sign_up_id.getText().toString().equals(AbleId.getId())){//id중복체크 한 아이디와 레트로핏 전 아이디가 같으면 통신
                            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://34.85.56.49:4000")
                                    .addConverterFactory(GsonConverterFactory.create()).build();

                            JoinApi joinApi = retrofit.create(JoinApi.class);

                            joinApi.postJoinUserInfo(input).enqueue(new Callback<JoinResult>() {
                                @Override
                                public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                                    if (response.isSuccessful()) {
                                        JoinResult map = response.body();
                                        if (map != null) {
                                            switch (map.getResult()) {
                                                case 1:
                                                    Toast.makeText(RegisterActivity.this, "회원 가입이 되었습니다~!", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    break;
                                                case 0:
                                                    Toast.makeText(RegisterActivity.this, "존재하는 아이디 입니다...", Toast.LENGTH_SHORT).show();
                                                    break;

                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JoinResult> call, Throwable t) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "중복체크한 ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }            }
        });
    }
}