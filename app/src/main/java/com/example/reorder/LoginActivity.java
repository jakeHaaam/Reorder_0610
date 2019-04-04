package com.example.reorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
            }
        });
    }
}
